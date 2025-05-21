import { useEffect, useState } from 'react';
import { dashboard } from '../lib/api';
import useAuthStore from '../store/authStore';
import toast from 'react-hot-toast';

export default function Dashboard() {
  const [data, setData] = useState<any>(null);
  const role = useAuthStore(state => state.role);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = role === 'ADMIN' 
          ? await dashboard.getAdminData()
          : await dashboard.getEmployeeData(1); // Replace with actual employee ID
        setData(response.data);
      } catch (error) {
        toast.error('Failed to load dashboard data');
      }
    };

    fetchData();
  }, [role]);

  if (!data) return <div>Loading...</div>;

  return (
    <div className="space-y-6">
      <h2 className="text-2xl font-bold text-gray-900">Dashboard</h2>
      
      {role === 'ADMIN' ? (
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
          <div className="bg-white p-6 rounded-lg shadow">
            <h3 className="text-lg font-semibold text-gray-700">Total Employees</h3>
            <p className="text-3xl font-bold text-indigo-600">{data.totalEmployees}</p>
          </div>
          <div className="bg-white p-6 rounded-lg shadow">
            <h3 className="text-lg font-semibold text-gray-700">Total Departments</h3>
            <p className="text-3xl font-bold text-indigo-600">{data.totalDepartments}</p>
          </div>
          <div className="bg-white p-6 rounded-lg shadow">
            <h3 className="text-lg font-semibold text-gray-700">Attendance Records</h3>
            <p className="text-3xl font-bold text-indigo-600">{data.totalAttendanceRecords}</p>
          </div>
        </div>
      ) : (
        <div className="bg-white p-6 rounded-lg shadow">
          <h3 className="text-lg font-semibold text-gray-700">Employee Information</h3>
          <div className="mt-4 space-y-2">
            <p><span className="font-semibold">Name:</span> {data.employeeName}</p>
            <p><span className="font-semibold">Department:</span> {data.departmentName}</p>
            <p><span className="font-semibold">Position:</span> {data.position}</p>
            <p><span className="font-semibold">Total Attendance Records:</span> {data.totalAttendanceRecords}</p>
          </div>
        </div>
      )}
    </div>
  );
}