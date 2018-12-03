import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICmContext } from 'app/shared/model/cm-context.model';

@Component({
    selector: 'jhi-cm-context-detail',
    templateUrl: './cm-context-detail.component.html'
})
export class CmContextDetailComponent implements OnInit {
    cmContext: ICmContext;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ cmContext }) => {
            this.cmContext = cmContext;
        });
    }

    previousState() {
        window.history.back();
    }
}
