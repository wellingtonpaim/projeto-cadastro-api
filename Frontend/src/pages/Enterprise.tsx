import Header from "../components/Header";
import NavBar from "../components/SideBar";
import { axiosPrivate } from "../api/axiosConfig";
import { useEffect, useState } from "react";
import CrudContainer from "../components/CrudContainer";
import InputText from "../components/Input/InputText";
import Button from "../components/Button/Button";
import type { EnterpriseData } from "../interface/EnterpriseData";
import InputTelephone from "../components/Input/InputTelephone";
import Line from "../components/Line";
import InputSelect from "../components/Input/InputSelect";
import { UF } from "../constants/UF";
import RequestError from "../components/Error/RequestError";
import ErrorMessage from "../components/Error/ErrorMessage";
import axios from "axios";

const Enterprise = () => {
  const [requestError, setRequestError] = useState<unknown | null>(null);
  const [formError, setFormError] = useState<string>("");

  const [enterpriseData, setEnterpriseData] = useState<EnterpriseData>({
    id: 0,
    razaoSocial: "",
    endereco: {
      cep: "",
      logradouro: "",
      numero: "",
      complemento: "",
      bairro: "",
      cidade: "",
      uf: "",
      enderecoFormatado: "",
    },
    cnpj: "",
    nomeFantasia: "",
    inscricaoEstadual: "",
    telefones: [{ tipo: "", ddd: 0, numero: 0 }],
    email: "",
    site: "",
    logotipoPath: "",
  });

  const handleUpdatePost = async () => {
    try {
      console.log("Dados sendo enviados no PUT:", enterpriseData);
      await axiosPrivate.put(`/empresa/8`, enterpriseData);
      setFormError("");
    } catch (error) {
      if (axios.isAxiosError(error)) {
        const apiMessage = error.response?.data?.message;
        setFormError(apiMessage);
        console.error(error);
      } else {
        setFormError("Erro desconhecido ao atualizar empresa.");
      }
    }
  };

  useEffect(() => {
    const fetchEnterpriseData = async () => {
      try {
        const response = await axiosPrivate.get("/empresa/8");
        setEnterpriseData(response.data.data);
        setRequestError(null);
      } catch (error) {
        setRequestError(error);
      }
    };

    fetchEnterpriseData();
  }, []);

  return (
    <div className="w-screen h-screen flex flex-col items-center justify-center overflow-hidden">
      <Header />
      <div className="size-full flex flex-1 flex-row overflow-hidden">
        <NavBar />
        <CrudContainer>
          <>
            <div className="flex flex-row justify-between items-center p-4 border-b border-gray">
              <div className="flex flex-row items-center gap-2">
                <h1 className="font-poppins font-semibold text-xl text-main">
                  Empresa
                </h1>
                <p className="font-poppins text-sm text-gray">
                  • Editar Instância
                </p>
              </div>
            </div>
            <div className="flex-1 overflow-y-auto flex flex-col p-4 gap-4">
              {requestError instanceof Error && (
                <RequestError
                  error={requestError}
                  customMessage="Erro ao carregar os clientes."
                />
              )}
              {formError && <ErrorMessage message={formError} />}
              <div className="w-full flex flex-row gap-6">
                <InputText
                  label="Nome da Empresa"
                  placeholder="Digite o Nome da Empresa..."
                  value={enterpriseData.nomeFantasia}
                  onChange={(e) =>
                    setEnterpriseData({
                      ...enterpriseData,
                      nomeFantasia: e.target.value,
                    })
                  }
                />
                <InputText
                  label="Razão Social"
                  placeholder="Digite a Razão Social da Empresa..."
                  value={enterpriseData.razaoSocial}
                  onChange={(e) =>
                    setEnterpriseData({
                      ...enterpriseData,
                      razaoSocial: e.target.value,
                    })
                  }
                />
              </div>
              <InputText
                label="CNPJ"
                placeholder="Digite o CNPJ da Empresa..."
                value={enterpriseData.cnpj}
                onChange={(e) =>
                  setEnterpriseData({
                    ...enterpriseData,
                    cnpj: e.target.value,
                  })
                }
              />
              <div className="w-full flex flex-row gap-4 items-center">
                <span className="font-poppins text-gray">Endereço</span>
                <Line />
              </div>
              <div className="w-full flex flex-row gap-6">
                <InputText
                  label="CEP"
                  placeholder="Digite o CEP do Cliente..."
                  value={enterpriseData.endereco.cep}
                  onChange={(e) =>
                    setEnterpriseData({
                      ...enterpriseData,
                      endereco: {
                        ...enterpriseData.endereco,
                        cep: e.target.value,
                      },
                    })
                  }
                />
                <InputText
                  label="Logradouro"
                  placeholder="Digite o Logradouro do Cliente..."
                  value={enterpriseData.endereco.logradouro}
                  onChange={(e) =>
                    setEnterpriseData({
                      ...enterpriseData,
                      endereco: {
                        ...enterpriseData.endereco,
                        logradouro: e.target.value,
                      },
                    })
                  }
                />
              </div>
              <div className="w-full flex flex-row gap-6">
                <InputText
                  label="Número"
                  placeholder="Digite o Número do Endereço do Cliente..."
                  value={enterpriseData.endereco.numero}
                  onChange={(e) =>
                    setEnterpriseData({
                      ...enterpriseData,
                      endereco: {
                        ...enterpriseData.endereco,
                        numero: e.target.value,
                      },
                    })
                  }
                />
                <InputText
                  label="Bairro"
                  placeholder="Digite o Bairro do Cliente..."
                  value={enterpriseData.endereco.bairro}
                  onChange={(e) =>
                    setEnterpriseData({
                      ...enterpriseData,
                      endereco: {
                        ...enterpriseData.endereco,
                        bairro: e.target.value,
                      },
                    })
                  }
                />
              </div>
              <div className="w-full flex flex-row gap-6">
                <InputText
                  label="Cidade"
                  placeholder="Digite a Cidade do Cliente..."
                  value={enterpriseData.endereco.cidade}
                  onChange={(e) =>
                    setEnterpriseData({
                      ...enterpriseData,
                      endereco: {
                        ...enterpriseData.endereco,
                        cidade: e.target.value,
                      },
                    })
                  }
                />
                <InputSelect
                  label="UF"
                  value={enterpriseData.endereco?.uf ?? ""}
                  onChange={(e) =>
                    setEnterpriseData({
                      ...enterpriseData,
                      endereco: {
                        ...enterpriseData.endereco,
                        uf: e.target.value,
                      },
                    })
                  }
                >
                  <option value="">Selecione</option>
                  {UF.map((UF, i) => (
                    <option key={i} value={UF.value}>
                      {UF.label}
                    </option>
                  ))}
                </InputSelect>
              </div>
              <div className="w-full flex flex-row gap-4 items-center">
                <span className="font-poppins text-gray">Contato</span>
                <Line />
              </div>
              <InputText
                label="Email"
                placeholder="Digite o Email do Cliente..."
                value={enterpriseData.email}
                onChange={(e) =>
                  setEnterpriseData({
                    ...enterpriseData,
                    email: e.target.value,
                  })
                }
              />
              <InputTelephone
                label="Telefones"
                placeholder="Digite o telefone..."
                value={enterpriseData.telefones}
                onChange={(newTelefones) =>
                  setEnterpriseData({
                    ...enterpriseData,
                    telefones: newTelefones,
                  })
                }
              />
            </div>
            <div className="w-full flex flex-row gap-4 justify-between items-center p-4 border-t border-gray">
              <Button onClick={handleUpdatePost}>
                <svg
                  width="15"
                  height="14"
                  viewBox="0 0 15 14"
                  fill="none"
                  xmlns="http://www.w3.org/2000/svg"
                >
                  <path
                    d="M10.8279 1.50136C10.9868 1.34241 11.1755 1.21632 11.3832 1.1303C11.5909 1.04428 11.8135 1 12.0383 1C12.263 1 12.4856 1.04428 12.6933 1.1303C12.901 1.21632 13.0897 1.34241 13.2486 1.50136C13.4076 1.66031 13.5337 1.84901 13.6197 2.05669C13.7057 2.26436 13.75 2.48695 13.75 2.71174C13.75 2.93653 13.7057 3.15912 13.6197 3.3668C13.5337 3.57447 13.4076 3.76317 13.2486 3.92212L5.07855 12.0922L1.75 13L2.65779 9.67145L10.8279 1.50136Z"
                    stroke="white"
                    strokeWidth="2"
                    strokeLinecap="round"
                    strokeLinejoin="round"
                  />
                </svg>

                <span className="font-poppins text-white font-semibold text-sm">
                  Salvar Alterações
                </span>
              </Button>
            </div>
          </>
        </CrudContainer>
      </div>
    </div>
  );
};

export default Enterprise;
