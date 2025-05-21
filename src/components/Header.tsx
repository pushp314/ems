import { useNavigate } from 'react-router-dom';
import useAuthStore from '../store/authStore';

export default function Header() {
  const navigate = useNavigate();
  const { name, logout } = useAuthStore();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <header className="bg-white shadow">
      <div className="flex justify-between items-center px-8 py-4">
        <h1 className="text-2xl font-semibold text-gray-900">Employee Management System</h1>
        <div className="flex items-center gap-4">
          <span className="text-gray-700">Welcome, {name}</span>
          <button
            onClick={handleLogout}
            className="px-4 py-2 text-sm text-white bg-red-600 rounded hover:bg-red-700"
          >
            Logout
          </button>
        </div>
      </div>
    </header>
  );
}