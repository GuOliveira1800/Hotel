import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ReservaService {
  private apiUrl = 'http://localhost:8080/reserva';

  constructor(private http: HttpClient) {}

  reqPost(body: any, endPoint: String): Observable<any> {
    return this.http.post(this.apiUrl+endPoint, body);
  }
}
