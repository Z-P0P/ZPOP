/**
 * 서버에 모임 리스트를 요청한다
 */
export default function (url) {
  return fetch(url)
    .then((res) => res.json())
    .catch((e) => {
      alert("잠시후에 시도해주세요"); //TODO: 처리
    });
}
