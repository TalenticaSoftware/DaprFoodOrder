const express = require('express');
const bodyParser = require('body-parser');
const app = express();
app.use(bodyParser.json());
const cors = require('cors');
const port = process.env.APP_PORT ?? '8083';

app.use(cors());

app.get('/v1/rating/restaurant/:restaurantId', (req, res) => {
 
  const restaurantId = req.params.restaurantId;
  console.log(`get the call for restaurantId : ${restaurantId}`);
  
  res.send({"rating" : 4});
});

app.listen(port, () => console.log(`Listening on port ${port}!`));