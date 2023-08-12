const { sequelize, QueryTypes } = require('../utils/sequelize');

const Location = require('../models/location');

async function getLocations(request, response) {
    const body = request.body;
    const uid = body.uid
    const latitude = body.latitude
    const longitude = body.longitude
    if (!uid || !latitude || !longitude) {
        response.status(400).json({ error: "Missing userUid parameter" });
    }
    try {
        const locations = await sequelize.query(`
SELECT u.uid, u.photo_url, u.firstname, u.lastname, u.nickname, ST_DistanceSphere(l.location, ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326)) AS distance
FROM users u
JOIN locations l ON u.uid = l.user_uid
WHERE l.enabled = true 
  AND u.uid <> :uid
  AND NOT EXISTS (
    SELECT 1
    FROM participants p
    JOIN rooms r ON p.room_uid = r.uid
    WHERE p.user_uid = u.uid
    AND r.uid IN (
        SELECT p.room_uid
        FROM participants p
        WHERE p.user_uid = :uid
    )
)
  AND l.last_updated >= NOW() - INTERVAL '1 month'
  AND ST_DistanceSphere(l.location, ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326)) <= 10000
ORDER BY distance
LIMIT 100
`, {
            type: QueryTypes.SELECT,
            replacements: {
                uid: uid,
                latitude: latitude,
                longitude: longitude
            },
        });

        response.status(200).json(locations);
    }
    catch (error) {
        console.error(error);
        response.status(500).json({ error: "Internal server error" });
    }
}

async function setLocationEnabled(request, response) {
    const query = request.query;
    const uid = query.uid;
    const enabled = query.enabled;
    if (!uid || !enabled) {
        response.status(400).json({ error: "Missing parameter" });
        return;
    }
    try {
        const result = await Location.update({
            enabled: enabled
        }, {
            where: {
                user_uid: uid
            }
        });
        if (result[0] === 0) {
            response.status(404).json({ error: "User not found" });
        } else {
            response.status(200).json(true);
        }
    }
    catch (error) {
        console.error(error);
        response.status(500).json({ error: "Internal server error" });
    }
}

async function setLocation(request, response) {
    const body = request.body;
    const uid = body.uid;
    const latitude = body.latitude;
    const longitude = body.longitude;
    if (!uid || !latitude || !longitude) {
        response.status(400).json({ error: "Missing parameter" });
        return;
    }
    try {
        Location.findOne({
            where: {
                user_uid: uid
            }
        }).then((location) => {
            if (location) {
                location.update({
                    location: sequelize.fn('ST_SetSRID', sequelize.fn('ST_MakePoint', longitude, latitude), 4326),
                    last_updated: Date.now(),
                    enabled: true
                });
            } else {
                Location.create({
                    user_uid: uid,
                    location: sequelize.fn('ST_SetSRID', sequelize.fn('ST_MakePoint', longitude, latitude), 4326),
                    last_updated: Date.now()
                });
            }
            response.status(200).json(true);
        }).catch((error) => {
            console.error(error);
        });

    }
    catch (error) {
        console.error(error);
        response.status(500).json({ error: "Internal server error" });
    }
}

module.exports = {
    getLocations,
    setLocation,
    setLocationEnabled
};
