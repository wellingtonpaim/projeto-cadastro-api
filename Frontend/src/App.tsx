import { useEffect, useMemo, useState, type FormEvent } from "react";
import { useNavigate } from "react-router-dom";
import { LoginService } from "./service/LoginService";
import { LogoBlackHorizontal } from "./assets";
import Line from "./components/Line";
import InputText from "./components/Input/InputText";
import InputPassword from "./components/Input/InputPassword";
import Button from "./components/Button/Button";
import ErrorMessage from "./components/Error/ErrorMessage";

function App() {
  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");

  const [error, setError] = useState("");

  const loginService = useMemo(() => new LoginService(), []);
  const navigate = useNavigate();

  const efetuarLogin = () => {
    setError("");
    loginService
      .login(email, senha)
      .then((response) => {
        localStorage.setItem("ACCESS_TOKEN", response.data);
        navigate("/clientes");
      })
      .catch(() => {
        setError("Email ou senha inválidos.");
      });
  };

  useEffect(() => {
    const token = localStorage.getItem("ACCESS_TOKEN");

    if (token) {
      navigate("/clientes");
    }
  }, [navigate]);

  const handleSubmit = (e: FormEvent) => {
    e.preventDefault();
    efetuarLogin();
  };

  return (
    <div className="w-screen h-screen bg-background p-16 flex items-center justify-center overflow-hidden">
      <div className="w-full max-w-[1440px] h-full bg-white rounded-lg p-2 flex flex-row gap-2 items-center justify-center shadow-xl">
        <div className="w-full h-full bg-[url(./src/assets/images/side-image.png)] bg-cover bg-no-repeat bg-center rounded-lg"></div>
        <form onSubmit={handleSubmit} className="w-full h-full px-32 gap-8 flex flex-col justify-center">
          <div className="flex flex-col gap-4">
            <img
              src={LogoBlackHorizontal}
              alt="Logo Sinergia"
              className="h-12 w-38"
            />
            <div className="flex flex-col gap-2">
              <h1 className="font-poppins font-semibold text-xl">
                Fazer Login
              </h1>
              <p className="font-poppins font-light text-sm">
                Realize Login para entrar na plataforma.
              </p>
            </div>
          </div>
          <Line />
          <div className="flex flex-col gap-6">
            <InputText
              label="Email"
              placeholder="Digite seu email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
            <InputPassword
              label="Senha"
              placeholder="Digite sua senha..."
              value={senha}
              onChange={(e) => setSenha(e.target.value)}
            />
          </div>
          {error && <ErrorMessage message={error} />}
          <Button type="submit">Fazer Login</Button>
        </form>
      </div>
    </div>
  );
}

export default App;
