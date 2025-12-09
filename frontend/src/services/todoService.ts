import api from "../utils/api";

const todoService = {
  async getAllByCreatedBy(userId: number) {
    const response = await api.get(`/${userId}`);
    console.log(response.data.data);
    return response.data.data;
  },

  async getByIdAndCreatedBy(userId: number, todoId: number) {
    const response = await api.get(`/${userId}/${todoId}`);
    console.log(response.data.data);
    return response.data.data;
  },

  async create(userId: number) {
    const response = await api.post(`/${userId}`);
    console.log(response.data.data);
    return response.data.data;
  },

  async edit(userId: number, todoId: number) {
    const response = await api.put(`/${userId}/${todoId}`);
    console.log(response.data.data);
    return response.data.data;
  },

  async delete(userId: number, todoId: number) {
    await api.delete(`/${userId}/${todoId}`);
  },
};

export default todoService;
