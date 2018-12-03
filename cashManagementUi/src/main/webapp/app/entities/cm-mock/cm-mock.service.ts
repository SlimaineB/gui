import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICmMock } from 'app/shared/model/cm-mock.model';

type EntityResponseType = HttpResponse<ICmMock>;
type EntityArrayResponseType = HttpResponse<ICmMock[]>;

@Injectable({ providedIn: 'root' })
export class CmMockService {
    public resourceUrl = SERVER_API_URL + 'api/cm-mocks';

    constructor(private http: HttpClient) {}

    create(cmMock: ICmMock): Observable<EntityResponseType> {
        return this.http.post<ICmMock>(this.resourceUrl, cmMock, { observe: 'response' });
    }

    update(cmMock: ICmMock): Observable<EntityResponseType> {
        return this.http.put<ICmMock>(this.resourceUrl, cmMock, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICmMock>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICmMock[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
