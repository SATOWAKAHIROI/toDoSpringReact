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
    <>
      <h1>ユーザー登録ページ</h1>
      <form onSubmit={handleButton}>
        {error && (
          <div>
            <p>{error}</p>
          </div>
        )}
        <label>名前</label>
        <input
          id="name"
          name="name"
          type="text"
          required
          placeholder="山田 太郎"
          value={formData.name}
          onChange={(e) => setFormData({ ...formData, name: e.target.value })}
        />
        <label>メールアドレス</label>
        <input
          id="email"
          name="email"
          type="email"
          required
          placeholder="test@example.com"
          value={formData.email}
          onChange={(e) => setFormData({ ...formData, email: e.target.value })}
        />
        <label>パスワード</label>
        <input
          id="password"
          name="password"
          type="password"
          required
          value={formData.password}
          onChange={(e) =>
            setFormData({ ...formData, password: e.target.value })
          }
        />
        <label>確認用パスワード</label>
        <input
          id="confirmPassword"
          name="confirmPassword"
          type="password"
          required
          value={formData.confirmPassword}
          onChange={(e) =>
            setFormData({ ...formData, confirmPassword: e.target.value })
          }
        />
        <button type="submit" disabled={loading}>
          {loading ? "登録中..." : "登録"}
        </button>
      </form>
    </>
  );
}
