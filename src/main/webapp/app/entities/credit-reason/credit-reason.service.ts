import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICreditReason } from 'app/shared/model/credit-reason.model';

type EntityResponseType = HttpResponse<ICreditReason>;
type EntityArrayResponseType = HttpResponse<ICreditReason[]>;

@Injectable({ providedIn: 'root' })
export class CreditReasonService {
    public resourceUrl = SERVER_API_URL + 'api/credit-reasons';

    constructor(protected http: HttpClient) {}

    create(creditReason: ICreditReason): Observable<EntityResponseType> {
        return this.http.post<ICreditReason>(this.resourceUrl, creditReason, { observe: 'response' });
    }

    update(creditReason: ICreditReason): Observable<EntityResponseType> {
        return this.http.put<ICreditReason>(this.resourceUrl, creditReason, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICreditReason>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICreditReason[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
