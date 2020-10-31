package dhl.Mocks;

public class SerializedJsonMock {
    public String divJson(){
        String divJson = "      \"divisions\": [\n" +
                "        {\n" +
                "          \"divisionId\": 0,\n" +
                "          \"divisionName\": \"Atlantic\",\n" +
                "          \"teams\": [\n" +
                "            {\n" +
                "              \"teamId\": 0,\n" +
                "              \"teamName\": \"Ontario\",\n" +
                "              \"generalManager\": \"Mathew\",\n" +
                "              \"headCoach\": {\n" +
                "                \"coachName\": \"Todd McLellan\",\n" +
                "                \"skating\": 0.1,\n" +
                "                \"shooting\": 0.5,\n" +
                "                \"checking\": 1.0,\n" +
                "                \"saving\": 0.2\n" +
                "              },\n" +
                "              \"lossPoint\": 0,\n" +
                "              \"teamPoint\": 0,\n" +
                "              \"players\": [\n" +
                "                {\n" +
                "                  \"playerId\": 0,\n" +
                "                  \"playerName\": \"Henry\",\n" +
                "                  \"position\": \"FORWARD\",\n" +
                "                  \"captain\": false,\n" +
                "                  \"playerStats\": {\n" +
                "                    \"age\": 20,\n" +
                "                    \"skating\": 10,\n" +
                "                    \"shooting\": 10,\n" +
                "                    \"checking\": 10,\n" +
                "                    \"saving\": 0\n" +
                "                  },\n" +
                "                  \"injurySystem\": {\n" +
                "                    \"isInjured\": false,\n" +
                "                    \"numberOfDaysInjured\": 0\n" +
                "                  }\n" +
                "                },\n" +
                "                {\n" +
                "                  \"playerId\": 0,\n" +
                "                  \"playerName\": \"Max\",\n" +
                "                  \"position\": \"GOALIE\",\n" +
                "                  \"captain\": true,\n" +
                "                  \"playerStats\": {\n" +
                "                    \"age\": 20,\n" +
                "                    \"skating\": 10,\n" +
                "                    \"shooting\": 10,\n" +
                "                    \"checking\": 10,\n" +
                "                    \"saving\": 0\n" +
                "                  },\n" +
                "                  \"injurySystem\": {\n" +
                "                    \"isInjured\": false,\n" +
                "                    \"numberOfDaysInjured\": 0\n" +
                "                  }\n" +
                "                }\n" +
                "              ]\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      ]\n";
        return divJson;
    }
    public String serializedJson(){
        String json = "{\n" +
                "  \"leagueName\": \"Dhl\",\n" +
                "  \"conferences\": [\n" +
                "    {\n" +
                "      \"conferenceName\": \"Western\",\n" +
                "      \"divisions\": [\n" +
                "        {\n" +
                "          \"divisionId\": 0,\n" +
                "          \"divisionName\": \"Atlantic\",\n" +
                "          \"teams\": [\n" +
                "            {\n" +
                "              \"teamId\": 0,\n" +
                "              \"teamName\": \"Ontario\",\n" +
                "              \"generalManager\": \"Mathew\",\n" +
                "              \"headCoach\": {\n" +
                "                \"coachName\": \"Todd McLellan\",\n" +
                "                \"skating\": 0.1,\n" +
                "                \"shooting\": 0.5,\n" +
                "                \"checking\": 1.0,\n" +
                "                \"saving\": 0.2\n" +
                "              },\n" +
                "              \"lossPoint\": 0,\n" +
                "              \"teamPoint\": 0,\n" +
                "              \"players\": [\n" +
                "                {\n" +
                "                  \"playerId\": 0,\n" +
                "                  \"playerName\": \"Henry\",\n" +
                "                  \"position\": \"FORWARD\",\n" +
                "                  \"captain\": false,\n" +
                "                  \"playerStats\": {\n" +
                "                    \"age\": 20,\n" +
                "                    \"skating\": 10,\n" +
                "                    \"shooting\": 10,\n" +
                "                    \"checking\": 10,\n" +
                "                    \"saving\": 0\n" +
                "                  },\n" +
                "                  \"injurySystem\": {\n" +
                "                    \"isInjured\": false,\n" +
                "                    \"numberOfDaysInjured\": 0\n" +
                "                  }\n" +
                "                },\n" +
                "                {\n" +
                "                  \"playerId\": 0,\n" +
                "                  \"playerName\": \"Max\",\n" +
                "                  \"position\": \"GOALIE\",\n" +
                "                  \"captain\": true,\n" +
                "                  \"playerStats\": {\n" +
                "                    \"age\": 20,\n" +
                "                    \"skating\": 10,\n" +
                "                    \"shooting\": 10,\n" +
                "                    \"checking\": 10,\n" +
                "                    \"saving\": 0\n" +
                "                  },\n" +
                "                  \"injurySystem\": {\n" +
                "                    \"isInjured\": false,\n" +
                "                    \"numberOfDaysInjured\": 0\n" +
                "                  }\n" +
                "                }\n" +
                "              ]\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ],\n" +
                "  \"freeAgents\": [\n" +
                "    {\n" +
                "      \"playerId\": 0,\n" +
                "      \"playerName\": \"Henry\",\n" +
                "      \"position\": \"FORWARD\",\n" +
                "      \"playerStats\": {\n" +
                "        \"age\": 20,\n" +
                "        \"skating\": 10,\n" +
                "        \"shooting\": 10,\n" +
                "        \"checking\": 10,\n" +
                "        \"saving\": 0\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"playerId\": 0,\n" +
                "      \"playerName\": \"Max\",\n" +
                "      \"position\": \"GOALIE\",\n" +
                "      \"playerStats\": {\n" +
                "        \"age\": 20,\n" +
                "        \"skating\": 10,\n" +
                "        \"shooting\": 10,\n" +
                "        \"checking\": 10,\n" +
                "        \"saving\": 0\n" +
                "      }\n" +
                "    }\n" +
                "  ],\n" +
                "  \"leagueValidation\": {},\n" +
                "  \"coaches\": [\n" +
                "    {\n" +
                "      \"coachName\": \"Todd McLellan\",\n" +
                "      \"skating\": 0.1,\n" +
                "      \"shooting\": 0.5,\n" +
                "      \"checking\": 1.0,\n" +
                "      \"saving\": 0.2\n" +
                "    },\n" +
                "    {\n" +
                "      \"coachName\": \"Todd McLellan\",\n" +
                "      \"skating\": 0.1,\n" +
                "      \"shooting\": 0.5,\n" +
                "      \"checking\": 1.0,\n" +
                "      \"saving\": 0.2\n" +
                "    }\n" +
                "  ],\n" +
                "  \"managers\": [\n" +
                "    {\n" +
                "      \"generalManagerName\": \"Todd McLellan\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"generalManagerName\": \"Todd McLellan1\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        return  json;
    }
}
