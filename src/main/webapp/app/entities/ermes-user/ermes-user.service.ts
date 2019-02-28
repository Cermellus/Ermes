import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IErmesUser } from 'app/shared/model/ermes-user.model';

type EntityResponseType = HttpResponse<IErmesUser>;
type EntityArrayResponseType = HttpResponse<IErmesUser[]>;

@Injectable({ providedIn: 'root' })
export class ErmesUserService {
    public resourceUrl = SERVER_API_URL + 'api/ermes-users';

    constructor(protected http: HttpClient) {}

    create(ermesUser: IErmesUser): Observable<EntityResponseType> {
        return this.http.post<IErmesUser>(this.resourceUrl, ermesUser, { observe: 'response' });
    }

    update(ermesUser: IErmesUser): Observable<EntityResponseType> {
        return this.http.put<IErmesUser>(this.resourceUrl, ermesUser, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IErmesUser>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IErmesUser[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
