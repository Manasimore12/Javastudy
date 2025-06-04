import { Injectable } from '@angular/core';

const TOKEN = "token";
const USER = "user";

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  constructor() { }

  static saveToken(token: string): void {
    if (typeof window !== 'undefined') {
      window.localStorage.removeItem(TOKEN);
      window.localStorage.setItem(TOKEN, token);
    }
  }

  static saveUser(user: any): void {
    if (typeof window !== 'undefined') {
      window.localStorage.removeItem(USER);
      window.localStorage.setItem(USER, JSON.stringify(user));
    }
  }

  static getToken(): string | null {
    if (typeof window !== 'undefined') {
      return window.localStorage.getItem(TOKEN);
    }
    return null;
  }

  static getUser(): any {
    if (typeof window !== 'undefined') {
      const data = window.localStorage.getItem(USER);
      return data ? JSON.parse(data) : null;
    }
    return null;
  }

  static getUserRole(): string {
    const user = this.getUser();
    return user?.role ?? '';
  }

  static isAdminLoggedIn(): boolean {
    if (typeof window === 'undefined') return false;
    return this.getToken() !== null && this.getUserRole() === "ADMIN";
  }

  static isCustomerLoggedIn(): boolean {
    if (typeof window === 'undefined') return false;
    return this.getToken() !== null && this.getUserRole() === "CUSTOMER";
  }

  static hasToken(): boolean {
    return typeof window !== 'undefined' && this.getToken() !== null;
  }

  static getUserId(): string {
    const user = this.getUser();
    return user?.id ?? "";
  }

  static signout(): void {
    if (typeof window !== 'undefined') {
      window.localStorage.removeItem(TOKEN);
      window.localStorage.removeItem(USER);
    }
  }
}
