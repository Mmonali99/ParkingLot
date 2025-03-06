import React, { useState, useEffect } from 'react';
import useApi from '../../hooks/useApi';
import Button from '../../components/Button/Button';
import './Profile.css';

const Profile = () => {
  const { request, data, error } = useApi();
  const [profile, setProfile] = useState({ username: '', email: '', role: '' });

  useEffect(() => {
    request('/api/users/me', 'GET').then(response => {
      setProfile({
        username: response.username,
        email: response.email,
        role: response.role
      });
    }).catch(() => {});
  }, [request]);

  const handleUpdate = async (e) => {
    e.preventDefault();
    try {
      await request('/api/users/me', 'PUT', profile);
      alert('Profile updated successfully');
    } catch (err) {
      console.error('Profile update failed:', err);
    }
  };

  return (
    <div className="profile">
      <form className="profile-form" onSubmit={handleUpdate}>
        <h2>Profile</h2>
        <input
          type="text"
          placeholder="Username"
          value={profile.username}
          onChange={(e) => setProfile({ ...profile, username: e.target.value })}
        />
        <input
          type="email"
          placeholder="Email"
          value={profile.email}
          onChange={(e) => setProfile({ ...profile, email: e.target.value })}
        />
        <input
          type="text"
          placeholder="Role"
          value={profile.role}
          disabled
        />
        {error && <p className="error">{error}</p>}
        <Button type="submit">Update Profile</Button>
      </form>
    </div>
  );
};

export default Profile;