<script setup>
import { reactive, ref, watch } from "vue";
import { useRouter } from "vue-router";
import { useMemberStore } from "@/stores/memberStore";
import ModalDefault from "@/components/modal/Default.vue";
import ModalChanged from "@/components/modal/Changed.vue";
import api from "@/api";

const router = useRouter();
const memberInfo = useMemberStore();
const phNickname = memberInfo.nickname; //placeholder에 표시될 닉네임
// 닉네임 입력 상태
const inputStatus = reactive({
  inputMessage: "",
  isNicknameValid: null,
  inputNickname: "",
});

watch(
  () => inputStatus.inputNickname,
  () => {
    if (inputStatus.inputNickname.length === 0) {
      editState.nickname = false;
      inputStatus.isNicknameValid = null;
      inputStatus.inputNickname = "";
      inputStatus.inputMessage = "";
    }
  }
);

// 모달 on/off
let nicknameConfirmModalOn = ref(false);
let finalConfirmModalOn = ref(false);

// 닉네임, 이미지 변경 여부
const editState = reactive({
  nickname: false,
  image: false,
  imageName: "",
});

// 닉네임 -----------------------------------------------------------------
/**
 * 닉네임 유효성 검사를 위한 nicknameChangeHandler 와
 * validateNickname()함수 추가
 */
function nicknameChangeHandler() {
  let timer;
  const delay = 300;

  if (timer) {
    clearTimeout(timer);
  }
  timer = setTimeout(function () {
    validateNickname(inputStatus.inputNickname);
  }, delay);
}

function validateNickname(nickname) {
  if (nickname.length === 0) return;
  const nicknameValidExp = /^(?=.*[a-zA-Z0-9가-힣])[a-zA-Z0-9가-힣]{0,10}$/;

  if (!nicknameValidExp.test(nickname)) {
    inputStatus.inputMessage = "닉네임은 한글, 숫자, 영어만 조합해주세요!";
    inputStatus.isNicknameValid = false;
    return;
  }
  const form = new FormData();
  form.append("nickname", nickname);
  const url = "/api/nickname/validation";
  const options = {
    method: "POST",
    body: form,
  };
  fetch(url, options)
    .then((response) => {
      if (response.ok) {
        return response.json();
      }
      throw Error();
    })
    .then((data) => {
      let result = data.result;
      if (result === "NICKNAME_VALID") {
        inputStatus.inputMessage = "사용할 수 있는 닉네임이에요!";
        inputStatus.isNicknameValid = true;
        editState.nickname = true;
        return;
      }
      if (result == "NICKNAME_ALREADY_USED") {
        inputStatus.inputMessage = "이미 사용중인 닉네임이에요!";
        inputStatus.isNicknameValid = false;
        return;
      }
    })
    .catch((err) => {
      console.log(err);
    });
}

// 프로필 이미지 -----------------------------------------------------------------
let file;
const fileInput = ref(null);
const imgSrc = ref("");
/**
 * 파일 선택 창을 띄움
 * fileInput = ref, 원본 값 value로 참조
 */
function openFileUpload(e) {
  fileInput.value.click();
}

/**
 * 이미지 upload & priview update하기
 */
function handleImageUpload(e) {
  //input file 숨어있음 사진 아이콘 알맹이 on click -> openFileUpload실행
  //upload 이미지 = file
  file = e.target.files[0];
  const reader = new FileReader();
  reader.readAsDataURL(file);
  uploadImage();
  // priview 업데이트
  reader.onload = function (e) {
    imgSrc.value = e.target.result;
  };
}

/**
 * 이미지 업로드 요청
 */
function uploadImage() {
  const form = new FormData();
  form.append("file", file);
  form.append("path", "/image/profile");
  const uploadUrl = "/api/upload/profile";
  const option = {
    method: "POST",
    body: form,
  };
  fetch(uploadUrl, option)
    .then((response) => response.json())
    .then((data) => {
      editState.image = true;
      editState.imageName = data.name;
      //showimageChangeModal(); //흑
    });
}

// 프로필 수정 저장하기 -----------------------------------------------------------------
const resultMsg = ref("");

function onClickSave() {
  // 닉네임 유효성 에러.
  if (inputStatus.isNicknameValid === false) {
    inputStatus.inputMessage = "유효하지 않은 닉네임으로 변경할 수 없어요!";
    return;
  }

  // 닉네임 변경이 포함되어 있을경우, 확인 모달을 띄운다.
  // 확인 모달의 "예"를 눌러야 요청이 간다.
  if (inputStatus.isNicknameValid && editState.nickname) {
    nicknameConfirmModalOn.value = true;
    return;
  }

  // 프로필 사진만 변경된 경우
  if (!editState.nickname && editState.image) {
    finalConfirmModalOn.value = true;
    save();
    return;
  }
}

// 닉네임 안내 확인 후 변경
function onClickChnageNicknameYes() {
  nicknameConfirmModalOn.value = false; // 닉네임 30일 안내 모달 off
  save();
}

function save() {
  const form = new FormData();
  form.append("nickname", inputStatus.inputNickname);
  if (editState.imageName) form.append("imageName", editState.imageName);

  const url = "/api/me/edit";
  const options = {
    method: "POST",
    body: form,
  };
  fetch(url, options).then((res) => {
    if (!res.ok) {
      resultMsg.value = "닉네임 변경은30일에 한 번만 가능해요.";
      finalConfirmModalOn.value = true;
      return;
    }
    api.auth.me();
    resultMsg.value = "프로필 수정이 완료되었어요! 🥰";
    finalConfirmModalOn.value = true;
  });
}

function onClickClose() {
  api.auth
    .me()
    .then((res) => res.json())
    .then((data) => {
      console.log(data);
      memberInfo.setInfo(data);
      router.replace("/my-profile");
    });
}
</script>

//TODO : 프로필 이미지 업로드, 삭제, 모바일 화면에서 케밥적용
<template>
  <!-- 닉네임 변경 확인하는 모달 -->
  <ModalDefault v-if="nicknameConfirmModalOn">
    <template #modal-body>
      <p>닉네임은 30일에 한 번만 변경가능해요.</p>
      <p class="confirm">정말 변경하시겠어요 ?😶</p>
    </template>
    <template #modal-footer>
      <div @click="nicknameConfirmModalOn = false">아니오</div>
      <div class="yes" @click="onClickChnageNicknameYes">예</div>
    </template>
  </ModalDefault>

  <!-- 프로필 수정 최종 확인 모달 -->
  <ModalChanged v-if="finalConfirmModalOn">
    <template #modal-body v-if="true">
      <p class="confirm">{{ resultMsg }}</p>
    </template>
    <template #modal-footer>
      <div @click="onClickClose">닫기</div>
    </template>
  </ModalChanged>

  <div class="my-profile">
    <div class="my-profile-container">
      <h2 class="profile__title">프로필 수정</h2>
      <div class="profile__image">
        <input
          @change="handleImageUpload($event)"
          type="file"
          accept="image/*"
          ref="fileInput"
          class="file-input"
        />

        <!-- imgSrc = imagepath로 -->
        <div v-if="imgSrc" class="profile__image--with-photo">
          <img :src="imgSrc" alt="" class="profile__image--img" />
          <div class="icon icon-camera" @click="openFileUpload"></div>
        </div>
        <div v-else="imgSrc" class="profile__image--no-photo">
          <div class="icon icon-camera" @click="openFileUpload"></div>
        </div>
      </div>

      <div class="input-text">
        <label class="input-text__label" for="input-text__content"></label>
        <div
          class="input-text__content-wrapper"
          v-bind:class="{
            'input-text__content-wrapper--correct':
              inputStatus.isNicknameValid == true,
            'input-text__content-wrapper--error input__message--appear':
              inputStatus.isNicknameValid == false,
          }"
        >
          <input
            class="input-text__content"
            type="text"
            maxlength="10"
            spellcheck="false"
            v-bind:placeholder="phNickname"
            v-model="inputStatus.inputNickname"
            @input="nicknameChangeHandler()"
          />
        </div>
        <span
          class="input__message"
          v-bind:class="{ 'input__message--appear': inputStatus.inputMessage }"
          v-bind:textContent="inputStatus.inputMessage"
        ></span>
      </div>
      <span
        class="btn-semiround profile__btn--save"
        @click.prevent="onClickSave"
        >저장하기</span
      >
    </div>
  </div>
</template>

<style scoped>
@import url(../../assets/css/form.css);
@import url(../../assets/css/member/mypage.css);

.file-input {
  display: none;
}

.profile__image--with-photo {
  position: relative;
}

.profile__image--img {
  width: 8.75rem;
  height: 8.75rem;
  border-radius: 50%;
}

.modal-default-wrap {
  z-index: 3;
}
.yes {
  color: var(--main-color);
  border-left: 1px solid var(--light-grey1);
}
.profile__image .icon {
  position: absolute;
  bottom: 4px;
  right: 3px;
}

.modal-default-wrap {
  z-index: 1;
}
.yes {
  color: var(--main-color);
  border-left: 1px solid var(--light-grey1);
}

:deep(.modal__body p) {
  margin: 4px 0;
}
:deep(.modal__body p.confirm) {
  margin-top: 10px;
}

:deep(.modal__body div) {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

:deep(.modal__footer) {
  border-top: 1px solid var(--light-grey1);
}

:deep(.modal__footer div) {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  padding: 16px 8px;
  cursor: pointer;
}

:deep(.modal__footer div:hover) {
  background-color: var(--light-grey1);
}
</style>
