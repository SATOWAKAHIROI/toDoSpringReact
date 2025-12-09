import Login from "./components/pages/auth/Login";
import Register from "./components/pages/auth/UserRegister";
import { AuthProvider } from "./components/contexts/AuthContext";
import {
  BrowserRouter as Router,
  Routes,
  Route,
  Navigate,
} from "react-router-dom";
import TodoList from "./components/pages/TodoList";

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
