import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICreditRequestStatus } from 'app/shared/model/credit-request-status.model';

type EntityResponseType = HttpResponse<ICreditRequestStatus>;
type EntityArrayResponseType = HttpResponse<ICreditRequestStatus[]>;

@Injectable({ providedIn: 'root' })
export class CreditRequestStatusService {
    public resourceUrl = SERVER_API_URL + 'api/credit-request-statuses';

    constructor(protected http: HttpClient) {}

    create(creditRequestStatus: ICreditRequestStatus): Observable<EntityResponseType> {
        return this.http.post<ICreditRequestStatus>(this.resourceUrl, creditRequestStatus, { observe: 'response' });
    }

    update(creditRequestStatus: ICreditRequestStatus): Observable<EntityResponseType> {
        return this.http.put<ICreditRequestStatus>(this.resourceUrl, creditRequestStatus, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICreditRequestStatus>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICreditRequestStatus[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
