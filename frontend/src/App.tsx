import { AuthProvider } from "./contexts/AuthContext";

function App() {
  return (
    <AuthProvider>
      <div>
        <h1>ToDo List</h1>
        <input type="text" placeholder="Add a new task" />
        <button>Add</button>
        <ul>
          <li>Task 1</li>
          <li>Task 2</li>
          <li>Task 3</li>
        </ul>
      </div>
    </AuthProvider>
  );
}

export default App;
