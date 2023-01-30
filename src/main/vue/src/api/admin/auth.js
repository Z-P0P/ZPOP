function login(data) {
  return fetch("/api/admin/auth/login", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data),
  });
}

function me() {
  return fetch(`/api/admin/auth/me`);
}

export default {
  login,
  me,
};
