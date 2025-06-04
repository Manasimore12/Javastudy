import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { StorageService } from '../../../auth/services/storage/storage.service';
const BASE_URL ="http://localhost:8080/"

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private http: HttpClient) { }



  getAllCars(): Observable<any>{
      return this.http.get(BASE_URL+"api/admin/cars", {
          headers: this.createAuthorizationHeader()
      })
    }
  
    createAuthorizationHeader(): HttpHeaders {
      let authHeaders: HttpHeaders = new HttpHeaders();
      return authHeaders.set(
        'Authorization',
        'Bearer '+ StorageService.getToken()
      );
    }
}
