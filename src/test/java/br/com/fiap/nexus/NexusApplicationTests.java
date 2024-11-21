package br.com.fiap.nexus;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

@SpringBootTest
class NexusApplicationTests {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Test
	void testDatabaseConnection() {
		try (Connection connection = dataSource.getConnection()) {
			Assertions.assertNotNull(connection, "A conexão com o banco de dados falhou!");
		} catch (Exception e) {
			Assertions.fail("Erro ao conectar com o banco de dados: " + e.getMessage());
		}
	}

	@Test
	void testTableExistence() {
		String[] expectedTables = {"TB_USUARIO", "TB_DEVICES", "TB_SENSOR", "TB_ENERGY"};

		try (Connection connection = dataSource.getConnection()) {
			ResultSet resultSet = connection.getMetaData().getTables(null, null, "%", new String[]{"TABLE"});
			boolean[] tableExists = new boolean[expectedTables.length];

			while (resultSet.next()) {
				String tableName = resultSet.getString("TABLE_NAME");
				for (int i = 0; i < expectedTables.length; i++) {
					if (expectedTables[i].equalsIgnoreCase(tableName)) {
						tableExists[i] = true;
					}
				}
			}

			for (int i = 0; i < expectedTables.length; i++) {
				Assertions.assertTrue(tableExists[i], "Tabela " + expectedTables[i] + " não encontrada no banco de dados!");
			}
		} catch (Exception e) {
			Assertions.fail("Erro ao verificar tabelas no banco de dados: " + e.getMessage());
		}
	}
	@Test
	void testSelectUsuario() {
		String sql = "SELECT * FROM TB_USUARIO WHERE username = 'teste_user'";

		try {
			// Inserir dados de teste primeiro, para garantir que o usuário está na tabela
			jdbcTemplate.update("INSERT INTO TB_USUARIO (username, password, email) VALUES ('teste_user', 'teste_pass', 'teste@teste.com')");

			// Executar a consulta
			List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);

			// Verificar se o resultado não é vazio e contém o usuário inserido
			Assertions.assertFalse(result.isEmpty(), "Nenhum usuário encontrado com o username 'teste_user'");
			Assertions.assertEquals("teste_user", result.get(0).get("username"), "O username retornado não corresponde.");

			// Remover o usuário após o teste
			jdbcTemplate.update("DELETE FROM TB_USUARIO WHERE username = 'teste_user'");

			System.out.println("Teste de SELECT na tabela TB_USUARIO executado com sucesso.");
		} catch (Exception e) {
			System.err.println("Erro ao consultar a tabela TB_USUARIO: " + e.getMessage());
			Assertions.fail("Erro ao consultar a tabela TB_USUARIO: " + e.getMessage());
		}

	}
}
