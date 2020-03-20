import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IIdea } from 'app/shared/model/idea.model';

type EntityResponseType = HttpResponse<IIdea>;
type EntityArrayResponseType = HttpResponse<IIdea[]>;

@Injectable({ providedIn: 'root' })
export class IdeaService {
  public resourceUrl = SERVER_API_URL + 'api/ideas';

  constructor(protected http: HttpClient) {}

  create(idea: IIdea): Observable<EntityResponseType> {
    return this.http.post<IIdea>(this.resourceUrl, idea, { observe: 'response' });
  }

  update(idea: IIdea): Observable<EntityResponseType> {
    return this.http.put<IIdea>(this.resourceUrl, idea, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IIdea>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IIdea[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
