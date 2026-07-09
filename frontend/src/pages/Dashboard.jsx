import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { getCurrentUser } from '../services/authService';
import { getSkillsByUser } from '../services/skillService';
import { getSentRequests, getReceivedRequests } from '../services/requestService';
import Loader from '../components/Loader';
import '../styles/Dashboard.css';

function Dashboard() {
  const user = getCurrentUser();
  const [stats, setStats] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    loadDashboardData();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const loadDashboardData = async () => {
    try {
      const [skills, sent, received] = await Promise.all([
        getSkillsByUser(user.id),
        getSentRequests(user.id),
        getReceivedRequests(user.id),
      ]);

      const allRequests = [...sent, ...received];

      setStats({
        totalSkills: skills.length,
        teachingSkills: skills.filter((s) => s.type === 'TEACH').length,
        learningSkills: skills.filter((s) => s.type === 'LEARN').length,
        pendingRequests: allRequests.filter((r) => r.status === 'PENDING').length,
        acceptedRequests: allRequests.filter((r) => r.status === 'ACCEPTED').length,
      });
    } catch (err) {
      setError('Could not load dashboard data. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  if (loading) return <Loader text="Loading your dashboard..." />;

  return (
    <div className="dashboard-page">
      <h2>Welcome back, {user.fullName}!</h2>

      {error && <p className="error-message">{error}</p>}

      {stats && (
        <div className="stats-grid">
          <div className="stat-card">
            <h3>{stats.totalSkills}</h3>
            <p>Total Skills</p>
          </div>
          <div className="stat-card">
            <h3>{stats.teachingSkills}</h3>
            <p>Teaching Skills</p>
          </div>
          <div className="stat-card">
            <h3>{stats.learningSkills}</h3>
            <p>Learning Skills</p>
          </div>
          <div className="stat-card">
            <h3>{stats.pendingRequests}</h3>
            <p>Pending Requests</p>
          </div>
          <div className="stat-card">
            <h3>{stats.acceptedRequests}</h3>
            <p>Accepted Requests</p>
          </div>
        </div>
      )}

      <div className="dashboard-actions">
        <Link to="/skills/add" className="btn-primary">
          + Add a Skill
        </Link>
        <Link to="/search" className="btn-secondary">
          Search Skills
        </Link>
        <Link to="/requests" className="btn-secondary">
          View Requests
        </Link>
      </div>
    </div>
  );
}

export default Dashboard;
