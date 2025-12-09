import api from "../utils/api";

const authService = {
  async login(email: string, password: string) {
    const resposne = await api.post("/users/login", { email, password });
    const { data } = resposne.data;

    if (data.token) {
      localStorage.setItem("token", data.token);
      localStorage.setItem("user", JSON.stringify(data.user));
    }

    return data;
  },

  async register(name: string, email: string, password: string) {
    const response = await api.post("/users", { name, email, password });
    return response.data;
  },

  logout() {
    localStorage.removeItem("token");
    localStorage.removeItem("user");
  },

  getCurrentUser() {
    const userStr = localStorage.getItem("user");
    return userStr ? JSON.parse(userStr) : null;
  },

  isAuthenticated() {
    return !!localStorage.getItem("token");
  },
};

export default authService;
