import { FormEvent, useState } from "react";
import { useAuth } from "../../hooks/useAuth";
import { useNavigate } from "react-router-dom";
import { AxiosError } from "axios";

type form = {
  name: string;
  email: string;
  password: string;
  confirmPassword: string;
};

export default function Register() {
  const [formData, setFormData] = useState<form>({
    name: "",
    email: "",
    password: "",
    confirmPassword: "",
  });
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<string>("");
  const { register } = useAuth();
  const navigate = useNavigate();

  const handleButton = async (e: FormEvent) => {
    e.preventDefault();
    setError("");

    if (formData.password !== formData.confirmPassword) {
      setError("パスワードが一致しません。");
      return;
    }

    if (formData.password.length < 8) {
      setError("パスワードは8文字以上で入力してください。");
      return;
    }

    setLoading(true);

    try {
      const response = await register(
        formData.name,
        formData.email,
        formData.password
      );
      console.log(response);
      if (!response.success) return;
      navigate("/login", {
        state: { message: "登録完了しました。ログインしてください。" },
      });
    } catch (err) {
      if (err instanceof AxiosError) {
        setError(err.response?.data?.error?.message || "登録に失敗しました。");
      } else {
        setError("登録に失敗しました。");
      }
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-blue-50 flex items-center justify-center">
      <div className="bg-white p-8 rounded-lg shadow-lg w-96">
        <h1 className="text-3xl font-bold text-blue-600 mb-6 text-center">ユーザー登録ページ</h1>
        <form onSubmit={handleButton} className="space-y-4">
          {error && (
            <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded">
              <p>{error}</p>
            </div>
          )}
          <div>
            <label className="block text-blue-700 font-semibold mb-2">名前</label>
            <input
              id="name"
              name="name"
              type="text"
              required
              placeholder="山田 太郎"
              value={formData.name}
              onChange={(e) => setFormData({ ...formData, name: e.target.value })}
              className="w-full px-4 py-2 border border-blue-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>
          <div>
            <label className="block text-blue-700 font-semibold mb-2">メールアドレス</label>
            <input
              id="email"
              name="email"
              type="email"
              required
              placeholder="test@example.com"
              value={formData.email}
              onChange={(e) => setFormData({ ...formData, email: e.target.value })}
              className="w-full px-4 py-2 border border-blue-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>
          <div>
            <label className="block text-blue-700 font-semibold mb-2">パスワード</label>
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
          <div>
            <label className="block text-blue-700 font-semibold mb-2">確認用パスワード</label>
            <input
              id="confirmPassword"
              name="confirmPassword"
              type="password"
              required
              value={formData.confirmPassword}
              onChange={(e) =>
                setFormData({ ...formData, confirmPassword: e.target.value })
              }
              className="w-full px-4 py-2 border border-blue-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>
          <button
            type="submit"
            disabled={loading}
            className="w-full bg-blue-600 text-white font-bold py-2 px-4 rounded-lg hover:bg-blue-700 disabled:bg-blue-300 disabled:cursor-not-allowed transition-colors"
          >
            {loading ? "登録中..." : "登録"}
          </button>
        </form>
      </div>
    </div>
  );
}
