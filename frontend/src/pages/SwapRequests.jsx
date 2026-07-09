import { useEffect, useState } from 'react';
import { getCurrentUser } from '../services/authService';
import {
  getSentRequests,
  getReceivedRequests,
  acceptRequest,
  rejectRequest,
} from '../services/requestService';
import RequestCard from '../components/RequestCard';
import Loader from '../components/Loader';
import '../styles/Requests.css';

function SwapRequests() {
  const user = getCurrentUser();
  const [tab, setTab] = useState('received');
  const [sent, setSent] = useState([]);
  const [received, setReceived] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    loadRequests();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const loadRequests = async () => {
    try {
      const [sentData, receivedData] = await Promise.all([
        getSentRequests(user.id),
        getReceivedRequests(user.id),
      ]);
      setSent(sentData);
      setReceived(receivedData);
    } catch (err) {
      setError('Could not load swap requests.');
    } finally {
      setLoading(false);
    }
  };

  const handleAccept = async (id) => {
    try {
      await acceptRequest(id);
      loadRequests();
    } catch (err) {
      setError('Could not accept request.');
    }
  };

  const handleReject = async (id) => {
    try {
      await rejectRequest(id);
      loadRequests();
    } catch (err) {
      setError('Could not reject request.');
    }
  };

  if (loading) return <Loader text="Loading swap requests..." />;

  const listToShow = tab === 'received' ? received : sent;

  return (
    <div className="requests-page">
      <h2>Swap Requests</h2>

      {error && <p className="error-message">{error}</p>}

      <div className="requests-tabs">
        <button
          className={tab === 'received' ? 'active-tab' : ''}
          onClick={() => setTab('received')}
        >
          Received ({received.length})
        </button>
        <button className={tab === 'sent' ? 'active-tab' : ''} onClick={() => setTab('sent')}>
          Sent ({sent.length})
        </button>
      </div>

      {listToShow.length === 0 ? (
        <p>No {tab} requests yet.</p>
      ) : (
        <div className="requests-list">
          {listToShow.map((request) => (
            <RequestCard
              key={request.id}
              request={request}
              showActions={tab === 'received'}
              onAccept={handleAccept}
              onReject={handleReject}
            />
          ))}
        </div>
      )}
    </div>
  );
}

export default SwapRequests;
