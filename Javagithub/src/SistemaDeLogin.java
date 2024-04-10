import java.sql.*;

public class SistemaDeLogin {
    private static final String URL_BANCO = "jdbc:mysql://localhost/login_system";
    private static final String USUARIO_BANCO = "root";
    private static final String SENHA_BANCO = "senha123";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bem-vindo ao sistema de login!");

        System.out.println("Por favor, insira seu CPF:");
        String cpf = scanner.nextLine();

        System.out.println("Por favor, insira sua senha:");
        String senha = scanner.nextLine();

        if (autenticar(cpf, senha)) {
            System.out.println("Login bem-sucedido!");
        } else {
            System.out.println("CPF ou senha incorretos.");
        }

        scanner.close();
    }

    private static boolean autenticar(String cpf, String senha) {
        Connection conexao = null;
        PreparedStatement consulta = null;
        ResultSet resultado = null;

        try {
            conexao = DriverManager.getConnection(URL_BANCO, USUARIO_BANCO, SENHA_BANCO);
            consulta = conexao.prepareStatement("SELECT * FROM usuarios WHERE cpf = ?");
            consulta.setString(1, cpf);
            resultado = consulta.executeQuery();

            if (resultado.next()) {
                String senhaArmazenada = resultado.getString("senha");
                return senha.equals(senhaArmazenada);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultado != null) {
                try {
                    resultado.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (consulta != null) {
                try {
                    consulta.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
}