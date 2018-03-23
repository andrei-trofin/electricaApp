
import controller.ClientController;
import model.Client;
import org.junit.Before;
import org.junit.Test;
import repository.DataManager;



public class Test2 {
    private ClientController sut;

    @Before
    public void setUp() {
        DataManager dm = new DataManager();
        sut = new ClientController(dm);
    }

    @Test
    public void createClientWithWrongId_sut_returnReasonAndDoNotAddClient() {
        // Given
        Client client = new Client("Gheorghe", "Aici", -4);
        int size = sut.getClientsSize();

        // When & Then
        assert(sut.AddClient(client.getName(), client.getAddress(), client.getId()) != null);
        assert(size == sut.getClientsSize());
    }

    @Test
    public void createClientWithCorrectData_sut_returnNullAndAddClient() {
        // Given
        Client client = new Client("Gheorghe", "Aici", 4);
        int size = sut.getClientsSize();

        // When & Then
        assert(sut.AddClient(client.getName(), client.getAddress(), client.getId()) == null);
        assert(size + 1 == sut.getClientsSize());
    }
}
