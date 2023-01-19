/**
 * 로그인 인증
 */
function me() {
  return fetch(`/api/login/me`);
}

export default {
  me,
};
