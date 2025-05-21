import { useEffect, useState } from 'react';
import { departments } from '../lib/api';
import toast from 'react-hot-toast';

export default function Departments() {
  const [departmentList, setDepartmentList] = useState([]);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [formData, setFormData] = useState({
    departmentName: '',
    description: ''
  });

  useEffect(() => {
    fetchDepartments();
  }, []);

  const fetchDepartments = async () => {
    try {
      const response = await departments.getAll();
      setDepartmentList(response.data);
    } catch (error) {
      toast.error('Failed to load departments');
    }
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await departments.create(formData);
      toast.success('Department created successfully');
      setIsModalOpen(false);
      fetchDepartments();
    } catch (error) {
      toast.error('Failed to create department');
    }
  };

  const handleDelete = async (id: number) => {
    try {
      await departments.delete(id);
      toast.success('Department deleted successfully');
      fetchDepartments();
    } catch (error) {
      toast.error('Failed to delete department');
    }
  };

  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <h2 className="text-2xl font-bold text-gray-900">Departments</h2>
        <button
          onClick={() => setIsModalOpen(true)}
          className="px-4 py-2 text-white bg-indigo-600 rounded hover:bg-indigo-700"
        >
          Add Department
        </button>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {departmentList.map((dept: any) => (
          <div key={dept.id} className="bg-white rounded-lg shadow p-6">
            <div className="flex justify-between items-start">
              <div>
                <h3 className="text-lg font-semibold text-gray-900">{dept.departmentName}</h3>
                <p className="text-sm text-gray-500 mt-1">{dept.description}</p>
              </div>
              <button
                onClick={() => handleDelete(dept.id)}
                className="text-red-600 hover:text-red-900"
              >
                Delete
              </button>
            </div>
            <div className="mt-4">
              <p className="text-sm text-gray-600">
                Employees: {dept.employeeCount}
              </p>
            </div>
          </div>
        ))}
      </div>

      {isModalOpen && (
        <div className="fixed inset-0 bg-gray-600 bg-opacity-50 flex items-center justify-center">
          <div className="bg-white p-8 rounded-lg shadow-lg w-full max-w-md">
            <h3 className="text-lg font-semibold mb-4">Add New Department</h3>
            <form onSubmit={handleSubmit} className="space-y-4">
              <div>
                <label className="block text-sm font-medium text-gray-700">Department Name</label>
                <input
                  type="text"
                  className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"
                  value={formData.departmentName}
                  onChange={(e) => setFormData({...formData, departmentName: e.target.value})}
                />
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700">Description</label>
                <textarea
                  className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"
                  rows={3}
                  value={formData.description}
                  onChange={(e) => setFormData({...formData, description: e.target.value})}
                />
              </div>
              <div className="flex justify-end gap-4">
                <button
                  type="button"
                  onClick={() => setIsModalOpen(false)}
                  className="px-4 py-2 text-gray-700 border border-gray-300 rounded hover:bg-gray-50"
                >
                  Cancel
                </button>
                <button
                  type="submit"
                  className="px-4 py-2 text-white bg-indigo-600 rounded hover:bg-indigo-700"
                >
                  Save
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
}