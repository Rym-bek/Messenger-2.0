const router = require("express").Router();
const locationController = require("../controllers/locationController");

router.post("/", /*tokenManager.verifyToken,*/ async (request, response) => {
    locationController.getLocations(request, response)
});

router.post("/update", /*tokenManager.verifyToken,*/ async (request, response) => {
    locationController.setLocation(request, response)
});

router.post("/enable", /*tokenManager.verifyToken,*/ async (request, response) => {
    locationController.setLocationEnabled(request, response)
});

module.exports = router;
