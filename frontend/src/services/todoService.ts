import api from "../utils/api";

const todoService = {
  async getAllByCreatedBy(userId: number) {
    const response = await api.get(`todo/${userId}`);
    return response.data.data;
  },

  async getByIdAndCreatedBy(userId: number, todoId: number) {
    const response = await api.get(`todo/${userId}/${todoId}`);
    return response.data.data;
  },

  async create(userId: number, title: string) {
    const response = await api.post(`todo/${userId}`, {
      title,
      completed: false,
    });
    return response.data.data;
  },

  async edit(
    userId: number,
    todoId: number,
    title: string,
    completed: boolean
  ) {
    const response = await api.put(`todo/${userId}/${todoId}`, {
      title,
      completed,
    });
    return response.data.data;
  },

  async delete(userId: number, todoId: number) {
    await api.delete(`todo/${userId}/${todoId}`);
  },
};

export default todoService;
