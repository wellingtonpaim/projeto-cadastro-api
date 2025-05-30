interface RequestErrorProps {
  error: unknown;
  customMessage?: string;
}

const RequestError = ({ error, customMessage }: RequestErrorProps) => {
  console.error("Erro:", error);

  return (
    <div className="flex items-center gap-2 p-4 bg-orange-100 border border-orange-300 rounded-xl text-orange-700 text-sm font-poppins">
      <svg
        width="19"
        height="19"
        viewBox="0 0 19 19"
        fill="none"
        xmlns="http://www.w3.org/2000/svg"
      >
        <path
          d="M9.33331 6.13331V9.33331M9.33331 12.5333H9.34131M17.3333 9.33331C17.3333 13.7516 13.7516 17.3333 9.33331 17.3333C4.91503 17.3333 1.33331 13.7516 1.33331 9.33331C1.33331 4.91503 4.91503 1.33331 9.33331 1.33331C13.7516 1.33331 17.3333 4.91503 17.3333 9.33331Z"
          stroke="#CA3500"
          strokeWidth="2"
          strokeLinecap="round"
          strokeLinejoin="round"
        />
      </svg>
      <span>{customMessage ?? "Erro ao processar requisição. Tente novamente."}</span>
    </div>
  );
};

export default RequestError;
