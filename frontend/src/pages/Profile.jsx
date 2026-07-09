import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { getCurrentUser } from '../services/authService';
import { getSkillsByUser, deleteSkill } from '../services/skillService';
import SkillCard from '../components/SkillCard';
import Loader from '../components/Loader';
import '../styles/Profile.css';

function Profile() {
  const user = getCurrentUser();
  const [skills, setSkills] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    loadSkills();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const loadSkills = async () => {
    try {
      const data = await getSkillsByUser(user.id);
      setSkills(data);
    } catch (err) {
      setError('Could not load your skills.');
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (skillId) => {
    if (!window.confirm('Delete this skill?')) return;
    try {
      await deleteSkill(skillId);
      setSkills(skills.filter((s) => s.id !== skillId));
    } catch (err) {
      setError('Could not delete this skill.');
    }
  };

  if (loading) return <Loader text="Loading your profile..." />;

  return (
    <div className="profile-page">
      <div className="profile-header">
        <div>
          <h2>{user.fullName}</h2>
          <p>{user.email}</p>
          <p>
            {user.college || 'College not set'} &middot; {user.department || 'Department not set'}{' '}
            {user.year ? `· Year ${user.year}` : ''}
          </p>
          {user.bio && <p className="profile-bio">{user.bio}</p>}
        </div>
        <Link to="/profile/edit" className="btn-secondary">
          Edit Profile
        </Link>
      </div>

      <div className="profile-skills-header">
        <h3>My Skills</h3>
        <Link to="/skills/add" className="btn-primary">
          + Add Skill
        </Link>
      </div>

      {error && <p className="error-message">{error}</p>}

      {skills.length === 0 ? (
        <p>You haven't added any skills yet.</p>
      ) : (
        <div className="skills-grid">
          {skills.map((skill) => (
            <SkillCard key={skill.id} skill={skill} onDelete={handleDelete} />
          ))}
        </div>
      )}
    </div>
  );
}

export default Profile;
