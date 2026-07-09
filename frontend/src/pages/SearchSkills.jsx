import { useState } from 'react';
import { searchUsers } from '../services/userService';
import { getCurrentUser } from '../services/authService';
import { sendSwapRequest } from '../services/requestService';
import Loader from '../components/Loader';
import '../styles/Search.css';

function SearchSkills() {
  const currentUser = getCurrentUser();
  const [filters, setFilters] = useState({ skillName: '', college: '', department: '' });
  const [results, setResults] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [message, setMessage] = useState('');

  const handleChange = (e) => {
    setFilters({ ...filters, [e.target.name]: e.target.value });
  };

  const handleSearch = async (e) => {
    e.preventDefault();
    setError('');
    setMessage('');
    setLoading(true);
    try {
      const data = await searchUsers(filters);
      setResults(data.filter((u) => u.id !== currentUser.id));
    } catch (err) {
      setError('Search failed. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  const handleSendRequest = async (receiverId) => {
    try {
      await sendSwapRequest({
        senderId: currentUser.id,
        receiverId,
        message: 'Hi! I would like to swap skills with you.',
      });
      setMessage('Swap request sent!');
    } catch (err) {
      setError(err.response?.data?.message || 'Could not send request.');
    }
  };

  return (
    <div className="search-page">
      <h2>Search Skills</h2>

      <form className="search-form" onSubmit={handleSearch}>
        <input
          type="text"
          name="skillName"
          placeholder="Skill name (e.g. React)"
          value={filters.skillName}
          onChange={handleChange}
        />
        <input
          type="text"
          name="college"
          placeholder="College"
          value={filters.college}
          onChange={handleChange}
        />
        <input
          type="text"
          name="department"
          placeholder="Department"
          value={filters.department}
          onChange={handleChange}
        />
        <button type="submit">Search</button>
      </form>

      {error && <p className="error-message">{error}</p>}
      {message && <p className="success-message">{message}</p>}

      {loading ? (
        <Loader text="Searching..." />
      ) : (
        <div className="search-results">
          {results.length === 0 ? (
            <p>No results yet. Try searching above.</p>
          ) : (
            results.map((user) => (
              <div className="user-result-card" key={user.id}>
                <h4>{user.fullName}</h4>
                <p>
                  {user.college || 'Unknown college'} &middot; {user.department || 'Unknown department'}
                </p>
                {user.bio && <p className="user-bio">{user.bio}</p>}
                <button onClick={() => handleSendRequest(user.id)}>Send Swap Request</button>
              </div>
            ))
          )}
        </div>
      )}
    </div>
  );
}

export default SearchSkills;
