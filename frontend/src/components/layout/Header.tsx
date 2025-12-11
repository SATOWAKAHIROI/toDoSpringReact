import { useNavigate } from "react-router-dom";
import { useAuth } from "../../hooks/useAuth";

export default function Header() {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate("/logout");
  };

  return (
    <header className="bg-blue-600 shadow-md">
      <div className="container mx-auto px-4 py-4 flex items-center justify-between">
        <h1 className="text-2xl font-bold text-white">Todo管理アプリ</h1>
        <div className="flex items-center gap-4">
          <span className="text-white font-semibold">{user!.name}</span>
          <button
            onClick={handleLogout}
            className="bg-white text-blue-600 font-bold py-2 px-4 rounded-lg hover:bg-blue-50 transition-colors"
          >
            ログアウト
          </button>
        </div>
      </div>
    </header>
  );
}
