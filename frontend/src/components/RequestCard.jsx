import '../styles/RequestCard.css';

// Displays a single swap request. showActions=true shows Accept/Reject
// buttons (used for received requests).
function RequestCard({ request, showActions, onAccept, onReject }) {
  return (
    <div className="request-card">
      <div className="request-card-header">
        <strong>{request.sender?.fullName}</strong> &rarr; <strong>{request.receiver?.fullName}</strong>
        <span className={`status-badge status-${request.status?.toLowerCase()}`}>
          {request.status}
        </span>
      </div>

      {request.message && <p className="request-message">"{request.message}"</p>}

      <p className="request-date">
        Sent on: {new Date(request.createdDate).toLocaleString()}
      </p>

      {showActions && request.status === 'PENDING' && (
        <div className="request-card-actions">
          <button onClick={() => onAccept(request.id)}>Accept</button>
          <button className="btn-danger" onClick={() => onReject(request.id)}>
            Reject
          </button>
        </div>
      )}
    </div>
  );
}

export default RequestCard;
