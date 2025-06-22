package br.com.thiagosv.cliente.controller.request;

import br.com.thiagosv.cliente.util.ConstantUtil;
import br.com.thiagosv.cliente.util.MockUtil;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("unitario")
class CriarClienteRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Deve validar todos os campos quando for uma requisicao valida")
    void deveValidarNomeQuandoForValido() {
        CriarClienteRequest request = MockUtil.criarClienteRequest();

        Set<ConstraintViolation<CriarClienteRequest>> violations = validator.validate(request);

        assertThat(violations).isEmpty();
    }

    @Nested
    @DisplayName("Testes para o campo nome")
    class NomeTests {

        @ParameterizedTest(name = "Caso {index}: nome={0}")
        @MethodSource("nomesNullOuBranco")
        @DisplayName("Deve rejeitar quando nome for muito branco ou null")
        void deveRejeitarQuandoNomeForEmBranco(String nomeInvalido) {
            CriarClienteRequest request = MockUtil.criarClienteRequest();
            request.setNome(nomeInvalido);

            Set<ConstraintViolation<CriarClienteRequest>> violations = validator.validate(request);

            assertThat(violations).hasSize(1);
            assertThat(violations.iterator().next().getMessage()).isEqualTo("O nome é obrigatório!");
        }

        private static Stream<String> nomesNullOuBranco() {
            return Stream.of(
                    null,
                    "  "
            );
        }

        @ParameterizedTest(name = "Caso {index}: nome={0}")
        @MethodSource("nomesCurtosOuLongos")
        @DisplayName("Deve rejeitar quando nome for muito curto ou longo")
        void deveRejeitarQuandoNomeForMuitoCurto(String nomeInvalido) {
            CriarClienteRequest request = MockUtil.criarClienteRequest();
            request.setNome(nomeInvalido);

            Set<ConstraintViolation<CriarClienteRequest>> violations = validator.validate(request);

            assertThat(violations).hasSize(1);
            assertThat(violations.iterator().next().getMessage()).isEqualTo("O nome deve ter entre 2 e 100 caracteres!");
        }

        private static Stream<String> nomesCurtosOuLongos() {
            return Stream.of(
                    "A",
                    "A".repeat(101)
            );
        }


        @Test
        @DisplayName("Deve rejeitar quando nome for invalido")
        void deveRejeitarQuandoNomeForInvalido() {
            CriarClienteRequest request = MockUtil.criarClienteRequest();
            request.setNome("");

            Set<ConstraintViolation<CriarClienteRequest>> violations = validator.validate(request);

            assertThat(violations).hasSize(2);
            assertThat(violations)
                    .extracting(ConstraintViolation::getMessage)
                    .containsExactlyInAnyOrder(
                            "O nome é obrigatório!",
                            "O nome deve ter entre 2 e 100 caracteres!"
                    );
        }
    }

    @Nested
    @DisplayName("Testes para o campo email")
    class EmailTests {

        @ParameterizedTest(name = "Deve rejeitar quando email for \"{0}\"")
        @MethodSource("emailsInvalidos")
        void deveRejeitarQuandoEmailForEmBrancoOuNull(String emailInvalido) {
            CriarClienteRequest request = MockUtil.criarClienteRequest();
            request.setEmail(emailInvalido);

            Set<ConstraintViolation<CriarClienteRequest>> violations = validator.validate(request);

            assertThat(violations).hasSize(1);
            assertThat(violations.iterator().next().getMessage()).isEqualTo("O e-mail é obrigatório!");
        }

        private static Stream<String> emailsInvalidos() {
            return Stream.of(
                    null,
                    ""
            );
        }

        @Test
        @DisplayName("Deve rejeitar quando email for inválido")
        void deveRejeitarQuandoEmailForInvalido() {
            CriarClienteRequest request = MockUtil.criarClienteRequest();
            request.setEmail("email-invalido");

            Set<ConstraintViolation<CriarClienteRequest>> violations = validator.validate(request);

            assertThat(violations).hasSize(1);
            assertThat(violations.iterator().next().getMessage()).isEqualTo("O e-mail deve ser válido!");
        }
    }

    @Nested
    @DisplayName("Testes para o campo dataNascimento")
    class DataNascimentoTests {

        @Test
        @DisplayName("Deve rejeitar quando dataNascimento for nula")
        void deveRejeitarQuandoDataNascimentoForNula() {
            CriarClienteRequest request = MockUtil.criarClienteRequest();
            request.setDataNascimento(null);

            Set<ConstraintViolation<CriarClienteRequest>> violations = validator.validate(request);

            assertThat(violations).hasSize(1);
            assertThat(violations.iterator().next().getMessage()).isEqualTo("A data de nascimento é obrigatória!");
        }
    }

    @Nested
    @DisplayName("Testes para o campo numeroCelular")
    class NumeroCelularTests {

        @Test
        @DisplayName("Deve rejeitar quando numeroCelular for null")
        void deveRejeitarQuandoNumeroCelularForNull() {
            CriarClienteRequest request = MockUtil.criarClienteRequest();
            request.setNumeroCelular(null);

            Set<ConstraintViolation<CriarClienteRequest>> violations = validator.validate(request);

            assertThat(violations).hasSize(1);
            assertThat(violations)
                    .extracting(ConstraintViolation::getMessage)
                    .containsExactlyInAnyOrder("O número é obrigatório!");
        }

        @ParameterizedTest
        @MethodSource("numerosCelularInvalidos")
        @DisplayName("Deve rejeitar quando numeroCelular for invalido")
        void deveRejeitarQuandoNumeroCeelularForInvalido(String numeroCelularInvalido) {
            CriarClienteRequest request = MockUtil.criarClienteRequest();
            request.setNumeroCelular(numeroCelularInvalido);

            Set<ConstraintViolation<CriarClienteRequest>> violations = validator.validate(request);

            assertThat(violations).hasSize(1);
            assertThat(violations)
                    .extracting(ConstraintViolation::getMessage)
                    .containsExactlyInAnyOrder("O número deve possuir 11 digítos, contando com o DDD");
        }

        private static Stream<String> numerosCelularInvalidos(){
            return Stream.of("5199999999","519999999999", "");
        }
    }

    @Nested
    @DisplayName("Testes para Getters e Setters")
    class GettersSettersTests {

        @Test
        @DisplayName("Deve usar getters e setters corretamente")
        void deveUsarGettersESettersCorretamente() {
            CriarClienteRequest request = new CriarClienteRequest();

            request.setNome(ConstantUtil.NOME_CLIENTE);
            request.setEmail(ConstantUtil.EMAIL_VALIDO);
            request.setDataNascimento(ConstantUtil.DATA_NASCIMENTO);
            request.setNumeroCelular(ConstantUtil.NUMERO_CELULAR);

            assertThat(request.getNome()).isEqualTo(ConstantUtil.NOME_CLIENTE);
            assertThat(request.getEmail()).isEqualTo(ConstantUtil.EMAIL_VALIDO);
            assertThat(request.getDataNascimento()).isEqualTo(ConstantUtil.DATA_NASCIMENTO);
            assertThat(request.getNumeroCelular()).isEqualTo(ConstantUtil.NUMERO_CELULAR);
        }
    }

    @Test
    @DisplayName("Deve criar instância através do construtor com todos os parâmetros")
    void deveCriarInstanciaAtravesDoConstrutorComTodosOsParametros() {
        String nome = ConstantUtil.NOME_CLIENTE;
        String email = ConstantUtil.EMAIL_VALIDO;

        CriarClienteRequest request = new CriarClienteRequest(
                nome,
                email,
                ConstantUtil.DATA_NASCIMENTO,
                ConstantUtil.NUMERO_CELULAR
        );

        assertThat(request.getNome()).isEqualTo(nome);
        assertThat(request.getEmail()).isEqualTo(email);
        assertThat(request.getDataNascimento()).isEqualTo(ConstantUtil.DATA_NASCIMENTO);
        assertThat(request.getNumeroCelular()).isEqualTo(ConstantUtil.NUMERO_CELULAR);
    }
}