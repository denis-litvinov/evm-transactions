# evm-transactions

The EVM Transactions project is a multi-module application that includes two services:

- **Storage Service** - Fetches real-time transactions from the Arbitrum network and stores them in a database.
- **Search Service** - Provides RESTful endpoints to search and retrieve transaction data.

---

## Services

### Storage service
- **Real-time Data Fetching**: Fetches live transactions from the Arbitrum network using Web3j.
- **Data Consistency**: Tracks the last processed block using Redis. If the service crashes, it resumes from the last saved block to ensure all data is consistent.
- **Batch Processing**: Gathers transactions in batches of 250 and persists them to the database for efficient processing.
- **Message Queue Integration**: Uses Kafka to send and receive transaction data, allowing for scalability and reliability.
- **Prometheus Monitoring**: Integrated Prometheus to monitor application metrics, including a custom metric that tracks the count of Kafka events sent. Prometheus runs on port 9090.

### Search service
- Provides RESTful endpoints to search transactions in the database.
- Allows filtering by transaction hash, sender address, recipient address, and block number.


---
## Getting started

### Environment Variables

Before running the services, ensure the following environment variable is set:

- API_KEY: Your Infura API key for the Arbitrum network. You can generate this key from Infura: https://app.infura.io/

### Running the application

1. Run the containers:
   ```bash
   docker-compose up

2. Start **Storage Service** to fetch records from arbitrum network and save them in DB.
3. Start **Search Service** to use REST-api to retrieve transactions data

---

## Endpoints
- `GET /api/transactions/hash/{hash}`: Retrieves a transaction by its unique hash.
- `GET /api/transactions/from/{fromAddress}`: Retrieves a list of transactions sent from the specified address.
- `GET /api/transactions/to/{toAddress}`: Retrieves a list of transactions sent to the specified address.
- `GET /api/transactions/block/{blockNumber}`: Retrieves all transactions included in the specified block.