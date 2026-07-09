import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { getCurrentUser } from '../services/authService';
import { addSkill } from '../services/skillService';
import '../styles/Auth.css';

function AddSkill() {
  const navigate = useNavigate();
  const user = getCurrentUser();

  const [formData, setFormData] = useState({
    skillName: '',
    level: 'Beginner',
    type: 'TEACH',
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
      await addSkill({ ...formData, userId: user.id });
      navigate('/profile');
    } catch (err) {
      setError(err.response?.data?.message || 'Could not add skill.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="auth-page">
      <form className="auth-form" onSubmit={handleSubmit}>
        <h2>Add a Skill</h2>

        {error && <p className="error-message">{error}</p>}

        <label>Skill Name</label>
        <input
          type="text"
          name="skillName"
          placeholder="e.g. React, Java, Public Speaking"
          value={formData.skillName}
          onChange={handleChange}
          required
        />

        <label>Level</label>
        <select name="level" value={formData.level} onChange={handleChange}>
          <option value="Beginner">Beginner</option>
          <option value="Intermediate">Intermediate</option>
          <option value="Advanced">Advanced</option>
        </select>

        <label>Type</label>
        <select name="type" value={formData.type} onChange={handleChange}>
          <option value="TEACH">I can teach this</option>
          <option value="LEARN">I want to learn this</option>
        </select>

        <button type="submit" disabled={loading}>
          {loading ? 'Adding...' : 'Add Skill'}
        </button>
      </form>
    </div>
  );
}

export default AddSkill;
