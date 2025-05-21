import { useEffect, useState } from 'react';
import { attendance } from '../lib/api';
import useAuthStore from '../store/authStore';
import toast from 'react-hot-toast';

export default function Attendance() {
  const [attendanceList, setAttendanceList] = useState([]);
  const role = useAuthStore(state => state.role);

  useEffect(() => {
    fetchAttendance();
  }, []);

  const fetchAttendance = async () => {
    try {
      const response = await attendance.getAll();
      setAttendanceList(response.data);
    } catch (error) {
      toast.error('Failed to load attendance records');
    }
  };

  const handleClockIn = async () => {
    try {
      await attendance.clockIn(1); // Replace with actual employee ID
      toast.success('Clocked in successfully');
      fetchAttendance();
    } catch (error) {
      toast.error('Failed to clock in');
    }
  };

  const handleClockOut = async (attendanceId: number) => {
    try {
      await attendance.clockOut(attendanceId);
      toast.success('Clocked out successfully');
      fetchAttendance();
    } catch (error) {
      toast.error('Failed to clock out');
    }
  };

  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <h2 className="text-2xl font-bold text-gray-900">Attendance</h2>
        {role === 'EMPLOYEE' && (
          <button
            onClick={handleClockIn}
            className="px-4 py-2 text-white bg-green-600 rounded hover:bg-green-700"
          >
            Clock In
          </button>
        )}
      </div>

      <div className="bg-white shadow rounded-lg">
        <table className="min-w-full divide-y divide-gray-200">
          <thead className="bg-gray-50">
            <tr>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Date</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Employee</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Clock In</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Clock Out</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Status</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Actions</th>
            </tr>
          </thead>
          <tbody className="bg-white divide-y divide-gray-200">
            {attendanceList.map((record: any) => (
              <tr key={record.id}>
                <td className="px-6 py-4 whitespace-nowrap">{record.date}</td>
                <td className="px-6 py-4 whitespace-nowrap">{record.employeeName}</td>
                <td className="px-6 py-4 whitespace-nowrap">{record.clockIn}</td>
                <td className="px-6 py-4 whitespace-nowrap">{record.clockOut || '-'}</td>
                <td className="px-6 py-4 whitespace-nowrap">{record.status}</td>
                <td className="px-6 py-4 whitespace-nowrap">
                  {!record.clockOut && (
                    <button
                      onClick={() => handleClockOut(record.id)}
                      className="text-blue-600 hover:text-blue-900"
                    >
                      Clock Out
                    </button>
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}