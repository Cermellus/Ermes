import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICreditReferenceType } from 'app/shared/model/credit-reference-type.model';

type EntityResponseType = HttpResponse<ICreditReferenceType>;
type EntityArrayResponseType = HttpResponse<ICreditReferenceType[]>;

@Injectable({ providedIn: 'root' })
export class CreditReferenceTypeService {
    public resourceUrl = SERVER_API_URL + 'api/credit-reference-types';

    constructor(protected http: HttpClient) {}

    create(creditReferenceType: ICreditReferenceType): Observable<EntityResponseType> {
        return this.http.post<ICreditReferenceType>(this.resourceUrl, creditReferenceType, { observe: 'response' });
    }

    update(creditReferenceType: ICreditReferenceType): Observable<EntityResponseType> {
        return this.http.put<ICreditReferenceType>(this.resourceUrl, creditReferenceType, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICreditReferenceType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICreditReferenceType[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
