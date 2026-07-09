import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { getCurrentUser } from '../services/authService';
import { updateUser } from '../services/userService';
import '../styles/Auth.css';

function EditProfile() {
  const navigate = useNavigate();
  const currentUser = getCurrentUser();

  const [formData, setFormData] = useState({
    fullName: currentUser.fullName || '',
    college: currentUser.college || '',
    department: currentUser.department || '',
    year: currentUser.year || '',
    bio: currentUser.bio || '',
    password: '',
  });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setLoading(true);
    try {
      const updated = await updateUser(currentUser.id, {
        ...formData,
        email: currentUser.email,
        year: formData.year ? Number(formData.year) : null,
      });
      // Keep localStorage in sync with the latest profile data
      localStorage.setItem('skillbridge_user', JSON.stringify(updated));
      navigate('/profile');
    } catch (err) {
      setError(err.response?.data?.message || 'Could not update profile.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="auth-page">
      <form className="auth-form" onSubmit={handleSubmit}>
        <h2>Edit Profile</h2>

        {error && <p className="error-message">{error}</p>}

        <label>Full Name</label>
        <input type="text" name="fullName" value={formData.fullName} onChange={handleChange} required />

        <label>College</label>
        <input type="text" name="college" value={formData.college} onChange={handleChange} />

        <label>Department</label>
        <input type="text" name="department" value={formData.department} onChange={handleChange} />

        <label>Year</label>
        <input type="number" name="year" min="1" max="5" value={formData.year} onChange={handleChange} />

        <label>Bio</label>
        <textarea name="bio" rows="3" value={formData.bio} onChange={handleChange} />

        <label>New Password (leave blank to keep current password)</label>
        <input type="password" name="password" value={formData.password} onChange={handleChange} />

        <button type="submit" disabled={loading}>
          {loading ? 'Saving...' : 'Save Changes'}
        </button>
      </form>
    </div>
  );
}

export default EditProfile;
