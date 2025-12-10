import Login from "./pages/auth/Login";
import Register from "./pages/auth/UserRegister";
import { AuthProvider } from "./contexts/AuthContext";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import TodoList from "./pages/TodoList";

function App() {
  return (
    <AuthProvider>
      <Router>
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/todo" element={<TodoList />} />
        </Routes>
      </Router>
    </AuthProvider>
  );
}

export default App;
