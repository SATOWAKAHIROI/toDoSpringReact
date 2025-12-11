import { FormEvent, useEffect, useState } from "react";
import { replace, useNavigate } from "react-router-dom";
import { AxiosError } from "axios";
import { useAuth } from "../../hooks/useAuth";

export default function Login() {
  type loginFormData = {
    email: string;
    password: string;
  };

  const [formData, setFormData] = useState<loginFormData>({
    email: "",
    password: "",
  });
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<string>("");
  const { login, isAuthenticated } = useAuth();
  const navigate = useNavigate();

  useEffect(() => {
    if (isAuthenticated) {
      navigate("/todo", { replace: true });
    }
  }, []);

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();
    setLoading(true);

    try {
      const response = await login(formData.email, formData.password);

      if (!response.user) {
        return;
      }

      navigate("/todo", {
        state: { message: "認証完了しました。" },
      });
    } catch (err) {
      if (err instanceof AxiosError) {
        setError(err.response?.data?.error?.message || "登録に失敗しました。");
      } else {
        setError("認証に失敗しました。");
      }
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-blue-50 flex items-center justify-center">
      <div className="bg-white p-8 rounded-lg shadow-lg w-96">
        <h1 className="text-3xl font-bold text-blue-600 mb-6 text-center">
          ログインページ
        </h1>
        <form onSubmit={handleSubmit} className="space-y-4">
          {error && (
            <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded">
              <p>{error}</p>
            </div>
          )}
          <div>
            <label className="block text-blue-700 font-semibold mb-2">
              メールアドレス
            </label>
            <input
              id="email"
              name="email"
              type="email"
              required
              placeholder="test@example.com"
              value={formData.email}
              onChange={(e) =>
                setFormData({ ...formData, email: e.target.value })
              }
              className="w-full px-4 py-2 border border-blue-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>
          <div>
            <label className="block text-blue-700 font-semibold mb-2">
              パスワード
            </label>
            <input
              id="password"
              name="password"
              type="password"
              required
              value={formData.password}
              onChange={(e) =>
                setFormData({ ...formData, password: e.target.value })
              }
              className="w-full px-4 py-2 border border-blue-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>
          <button
            type="submit"
            disabled={loading}
            className="w-full bg-blue-600 text-white font-bold py-2 px-4 rounded-lg hover:bg-blue-700 disabled:bg-blue-300 disabled:cursor-not-allowed transition-colors"
          >
            {loading ? "送信中..." : "送信"}
          </button>
        </form>
      </div>
    </div>
  );
}
