import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICreditReturnType } from 'app/shared/model/credit-return-type.model';

type EntityResponseType = HttpResponse<ICreditReturnType>;
type EntityArrayResponseType = HttpResponse<ICreditReturnType[]>;

@Injectable({ providedIn: 'root' })
export class CreditReturnTypeService {
    public resourceUrl = SERVER_API_URL + 'api/credit-return-types';

    constructor(protected http: HttpClient) {}

    create(creditReturnType: ICreditReturnType): Observable<EntityResponseType> {
        return this.http.post<ICreditReturnType>(this.resourceUrl, creditReturnType, { observe: 'response' });
    }

    update(creditReturnType: ICreditReturnType): Observable<EntityResponseType> {
        return this.http.put<ICreditReturnType>(this.resourceUrl, creditReturnType, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICreditReturnType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICreditReturnType[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
