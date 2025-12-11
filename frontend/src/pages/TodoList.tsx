import { FormEvent, useEffect, useState } from "react";
import todoService from "../services/todoService";
import { useAuth } from "../hooks/useAuth";
import { AxiosError } from "axios";

type Todo = {
  id: number;
  title: string;
  completed: boolean;
};

export default function TodoList() {
  const [title, setTitle] = useState<string>("");
  const [todoList, setTodoList] = useState<Todo[]>([]);
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<string>("");
  const [showModal, setShowModal] = useState<boolean>(false);
  const [formData, setFormData] = useState({
    title: "",
    completed: false,
  });
  const [editingTodo, setEditingTodo] = useState<Todo | null>(null);
  const { user } = useAuth();

  useEffect(() => {
    fetchTodoList();
  }, []);

  const fetchTodoList = async () => {
    setTodoList(await todoService.getAllByCreatedBy(user!.id));
  };

  const handleEdit = (todo: Todo) => {
    setEditingTodo(todo);
    setFormData({
      title: todo.title,
      completed: todo.completed,
    });
    setShowModal(true);
  };

  const handleDelete = async (todo: Todo) => {
    setLoading(true);
    try {
      await todoService.delete(user!.id, todo.id);
    } catch (err) {
      if (err instanceof AxiosError) {
        setError(
          err.response?.data?.error?.message || "タスクの削除に失敗しました。"
        );
      } else {
        setError("タスクの削除に失敗しました。");
      }
    } finally {
      setLoading(false);
      fetchTodoList();
    }
  };

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();
    setLoading(true);
    try {
      if (!!editingTodo) {
        await todoService.edit(
          user!.id,
          editingTodo.id,
          formData.title,
          formData.completed
        );
      } else {
        await todoService.create(user!.id, title);
      }
    } catch (err) {
      if (err instanceof AxiosError) {
        setError(
          err.response?.data?.error?.message ||
            "タスクの作成、編集に失敗しました。"
        );
      } else {
        setError("タスクの作成、編集に失敗しました。");
      }
    } finally {
      setLoading(false);
      setEditingTodo(null);
      setShowModal(false);
      setTitle("");
      fetchTodoList();
    }
  };

  return (
    <div className="min-h-screen bg-blue-50 flex items-center justify-center p-4">
      <div className="bg-white p-8 rounded-lg shadow-lg w-full max-w-2xl">
        <h1 className="text-3xl font-bold text-blue-600 mb-6 text-center">
          ToDo List
        </h1>
        <form onSubmit={handleSubmit} className="space-y-4 mb-8">
          {error && (
            <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded">
              <p>{error}</p>
            </div>
          )}
          <div className="flex gap-2">
            <input
              id="title"
              name="title"
              type="text"
              placeholder="Add a new task"
              required
              value={title}
              onChange={(e) => setTitle(e.target.value)}
              className="flex-1 px-4 py-2 border border-blue-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
            <button
              type="submit"
              disabled={loading}
              className="bg-blue-600 text-white font-bold py-2 px-6 rounded-lg hover:bg-blue-700 disabled:bg-blue-300 disabled:cursor-not-allowed transition-colors"
            >
              {loading ? "作成中" : "作成"}
            </button>
          </div>
        </form>
        <ul className="space-y-2">
          {todoList.length > 0 ? (
            todoList.map((todo) => (
              <li
                key={todo.id}
                className="flex items-center justify-between p-4 bg-blue-50 rounded-lg border border-blue-200"
              >
                <div className="flex items-center gap-3 flex-1">
                  <input
                    type="checkbox"
                    checked={todo.completed}
                    disabled={true}
                    className="w-5 h-5 text-blue-600 rounded focus:ring-2 focus:ring-blue-500"
                  />
                  <span className={`${todo.completed ? 'line-through text-gray-500' : 'text-gray-800'}`}>
                    {todo.title}
                  </span>
                </div>
                <div className="flex gap-2">
                  <button
                    onClick={() => handleEdit(todo)}
                    className="bg-yellow-500 text-white font-semibold py-1 px-4 rounded-lg hover:bg-yellow-600 transition-colors"
                  >
                    編集
                  </button>
                  <button
                    onClick={() => handleDelete(todo)}
                    className="bg-red-500 text-white font-semibold py-1 px-4 rounded-lg hover:bg-red-600 transition-colors"
                  >
                    削除
                  </button>
                </div>
              </li>
            ))
          ) : (
            <div className="text-center py-8 text-gray-500">
              <p>タスクが登録されていません</p>
            </div>
          )}
        </ul>

        {showModal && (
          <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center p-4">
            <div className="bg-white p-8 rounded-lg shadow-lg w-96">
              <h2 className="text-2xl font-bold text-blue-600 mb-6 text-center">
                タスク編集
              </h2>
              <form onSubmit={handleSubmit} className="space-y-4">
                <div>
                  <label className="block text-blue-700 font-semibold mb-2">
                    タイトル
                  </label>
                  <input
                    id="editTitle"
                    name="editTitle"
                    type="text"
                    value={formData.title}
                    onChange={(e) => {
                      setFormData({ ...formData, title: e.target.value });
                    }}
                    className="w-full px-4 py-2 border border-blue-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                </div>
                <div className="flex items-center gap-2">
                  <input
                    id="editCompleted"
                    name="editCompleted"
                    type="checkbox"
                    checked={formData.completed}
                    onChange={() =>
                      setFormData({ ...formData, completed: !formData.completed })
                    }
                    className="w-5 h-5 text-blue-600 rounded focus:ring-2 focus:ring-blue-500"
                  />
                  <label htmlFor="editCompleted" className="text-blue-700 font-semibold">
                    完了
                  </label>
                </div>
                <div className="flex gap-2">
                  <button
                    type="button"
                    onClick={() => setShowModal(false)}
                    className="flex-1 bg-gray-400 text-white font-bold py-2 px-4 rounded-lg hover:bg-gray-500 transition-colors"
                  >
                    キャンセル
                  </button>
                  <button
                    type="submit"
                    disabled={loading}
                    className="flex-1 bg-blue-600 text-white font-bold py-2 px-4 rounded-lg hover:bg-blue-700 disabled:bg-blue-300 disabled:cursor-not-allowed transition-colors"
                  >
                    {loading ? "編集中..." : "編集"}
                  </button>
                </div>
              </form>
            </div>
          </div>
        )}
      </div>
    </div>
  );
}
