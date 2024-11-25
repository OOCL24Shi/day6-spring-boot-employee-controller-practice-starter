### **1. GET: Obtain the company list with employees**

- **HTTP Method**: `GET`

- **URI**: `/companies`

- **Response**:

  - **Status Code**: `200 OK`

  - **Body**:

    ```Json
    [
      {
        "registrationNumber":123,
        "name": "string",
        "employees": [
          { "employeeID":123, "name": "string", "position": "string" },
          { "registrationNumber":123, "name": "string", "position": "string" }
        ]
      },
      {
        "registrationNumber":456,
        "name": "string",
        "employees": []
      }
    ]
    ```



### **2. GET: Obtain a certain company with employees**

- **HTTP Method**: `GET`

- **URI**: `/companies/{id}`

- **Response**:

  - **Status Code**: `200 OK`

  - **Body**:

    ```json
    {
      "registrationNumber":123,
      "name": "string",
      "employees": [
        { "employeeID":123, "name": "string", "position": "string" },
        { "employeeID":456, "name": "string", "position": "string" }
      ]
    }
    ```



### **3. GET: Obtain the list of employees under a specific company**

- **HTTP Method**: `GET`

- **URI**: /companies/{id}/employees

- **Response**:

  - **Status Code**: `200 OK`

  - **Body**:

    ```json
    [
      { "employeeID":123, "name": "string", "position": "string" },
      { "employeeID":456, "name": "string", "position": "string" }
    ]
    ```



### **4. GET: Page query for company list**

- **HTTP Method**: `GET`

- **URI**: /companies?page={page}&size={size}

- **Response**:

  - **Status Code**: `200 OK`

  - **Body**:

    ```json
    {
      "page": 1,
      "size": 5,
      "content": [
        {
          "registrationNumber":123,
          "name": "string",
          "employees": [
            { "employeeID":123, "name": "string", "position": "string" },
            { "employeeID":456, "name": "string", "position": "string" }
          ]
        },
        {
          "registrationNumber":456,
          "name": "string",
          "employees": []
        }
      ]
    }
    ```





### **5. PUT: Update an employee under a company**

- **HTTP Method**: `PUT`

- **URI**: /companies/{companyId}/employees/{employeeId}

- **Request Body**:

  ```json
  {
    "name": "string",
    "position": "string"
  }
  ```

- **Response**:

  - **Status Code**: `200 OK`

  - **Body**:

    ```json
    {
      "employeeID":123,
      "name": "string",
      "position": "string",
      "companyId": "string"
    }
    ```



------

### **6. POST: Add a new company**

- **HTTP Method**: `POST`

- **URI**: `/companies`

- **Request Body**:

  ```json
  {
    "name": "string",
    "employees": [
      { "employeeID":123, "name": "string", "position": "string" }
    ]
  }
  ```

- **Response**:

  - **Status Code**: `201 Created`

  - **Body**:

    ```json
    {
      "registrationNumber":123,
      "name": "string",
      "employees": [
        { "employeeID":123, "name": "string", "position": "string" }
      ]
    }
    ```



### 7**. DELETE: Delete a company and its employees**

- **HTTP Method**: `DELETE`

- **URI**: /companies/{id}

- **Response**:

  - **Status Code**: `204 No Content` 

  - **Error**: If the company is not found:

    - **Status Code**: `404 Not Found`

    - **Body**:

      ```json
      { "error": "Company not found" }
      ```