const predict = async (req, res) => {
    try {
      const jsonData = req.body;
      const jsonString = JSON.stringify(jsonData);
  
      const model = await tf.loadLayersModel(
        "https://storage.googleapis.com/here-bucket/model.json"
      );
  
      const arrayData = Object.values(jsonData);
      const numData = arrayData.map((str) => parseInt(str));
  
      const data = Array.from({ length: 100 }, (value, index) =>
        index < numData.length ? numData[index] : 0
      );
  
      const input = tf.tensor2d(data, [1, 100]);
      const prediction = model.predict(input);
  
      const output = Array.from(await prediction.data());
  
      // Mengambil hasil prediksi dan menentukan Place_name berdasarkan City dan weather
      const city = jsonData.City;
      const weather = jsonData.weather;
  
      const placeName = getPlaceName(city, weather);
  
      res.json({ placeName });
    } catch (error) {
      console.error("Error:", error);
      res.status(500).json({ error: "Something went wrong" });
    }
  };
  
  // Fungsi untuk menentukan Place_name berdasarkan City dan weather
  function getPlaceName(city, weather) {
    // Lakukan logika atau pemrosesan sesuai dengan data yang diterima
    // Misalnya, Anda dapat menggunakan kondisi if-else atau switch-case
  
    // Contoh sederhana: Jika City adalah "Jakarta" dan weather adalah "Cerah", maka Place_name adalah "Monas"
    if (city === "Jakarta" && weather === "Cerah") {
      return "Monas";
    }
  
    // Jika kondisi lainnya, tambahkan logika atau pemrosesan yang sesuai di sini
  
    // Jika tidak ada kondisi yang cocok, Anda dapat mengembalikan nilai default atau pesan error
    return "Place not found";
  }
  