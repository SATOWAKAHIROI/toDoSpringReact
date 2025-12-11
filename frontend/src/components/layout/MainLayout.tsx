import { ReactNode } from "react";
import Header from "./Header";

export default function MainLayout({ children }: { children: ReactNode }) {
  return (
    <div className="min-h-screen flex flex-col">
      <Header></Header>
      <main className="flex-1">{children}</main>
    </div>
  );
}
