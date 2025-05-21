import { NavLink } from 'react-router-dom';
import useAuthStore from '../store/authStore';

export default function Sidebar() {
  const role = useAuthStore(state => state.role);

  return (
    <div className="fixed w-64 h-full bg-gray-800">
      <div className="flex flex-col h-full">
        <div className="flex items-center justify-center h-16 bg-gray-900">
          <span className="text-white text-xl font-semibold">EMS</span>
        </div>
        <nav className="flex-1 px-2 py-4 space-y-2">
          <NavLink
            to="/"
            className={({ isActive }) =>
              `flex items-center px-4 py-2 text-gray-300 rounded-lg hover:bg-gray-700 ${
                isActive ? 'bg-gray-700' : ''
              }`
            }
          >
            Dashboard
          </NavLink>
          {role === 'ADMIN' && (
            <NavLink
              to="/employees"
              className={({ isActive }) =>
                `flex items-center px-4 py-2 text-gray-300 rounded-lg hover:bg-gray-700 ${
                  isActive ? 'bg-gray-700' : ''
                }`
              }
            >
              Employees
            </NavLink>
          )}
          <NavLink
            to="/departments"
            className={({ isActive }) =>
              `flex items-center px-4 py-2 text-gray-300 rounded-lg hover:bg-gray-700 ${
                isActive ? 'bg-gray-700' : ''
              }`
            }
          >
            Departments
          </NavLink>
          <NavLink
            to="/attendance"
            className={({ isActive }) =>
              `flex items-center px-4 py-2 text-gray-300 rounded-lg hover:bg-gray-700 ${
                isActive ? 'bg-gray-700' : ''
              }`
            }
          >
            Attendance
          </NavLink>
        </nav>
      </div>
    </div>
  );
}