import { Link } from 'react-router-dom';
import { getCurrentUser } from '../services/authService';
import '../styles/Home.css';

function Home() {
  const user = getCurrentUser();

  return (
    <div className="home-page">
      <section className="hero">
        <h1>Welcome to SkillBridge</h1>
        <p>
          A platform where students exchange skills — teach what you know,
          learn what you don't.
        </p>

        {user ? (
          <Link to="/dashboard" className="btn-primary">
            Go to Dashboard
          </Link>
        ) : (
          <div className="hero-actions">
            <Link to="/register" className="btn-primary">
              Get Started
            </Link>
            <Link to="/login" className="btn-secondary">
              Login
            </Link>
          </div>
        )}
      </section>

      <section className="how-it-works">
        <h2>How it works</h2>
        <div className="how-it-works-grid">
          <div className="how-card">
            <h3>1. Add your skills</h3>
            <p>List skills you can teach and skills you want to learn.</p>
          </div>
          <div className="how-card">
            <h3>2. Search & find a match</h3>
            <p>Search other students by skill, college, or department.</p>
          </div>
          <div className="how-card">
            <h3>3. Send a swap request</h3>
            <p>Connect with a match and start swapping skills.</p>
          </div>
        </div>
      </section>
    </div>
  );
}

export default Home;
