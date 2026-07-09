import '../styles/SkillCard.css';

// Displays a single skill. Optionally shows Edit/Delete buttons
// (used on "My Profile" page) or a "Send Request" button (used on "Search" page).
function SkillCard({ skill, onEdit, onDelete, onRequest }) {
  return (
    <div className={`skill-card skill-card-${skill.type?.toLowerCase()}`}>
      <div className="skill-card-header">
        <h4>{skill.skillName}</h4>
        <span className="skill-badge">{skill.type}</span>
      </div>
      <p className="skill-level">Level: {skill.level || 'Not specified'}</p>

      {skill.user && (
        <p className="skill-owner">
          By: {skill.user.fullName} {skill.user.college ? `(${skill.user.college})` : ''}
        </p>
      )}

      <div className="skill-card-actions">
        {onEdit && <button onClick={() => onEdit(skill)}>Edit</button>}
        {onDelete && (
          <button className="btn-danger" onClick={() => onDelete(skill.id)}>
            Delete
          </button>
        )}
        {onRequest && <button onClick={() => onRequest(skill)}>Send Request</button>}
      </div>
    </div>
  );
}

export default SkillCard;
