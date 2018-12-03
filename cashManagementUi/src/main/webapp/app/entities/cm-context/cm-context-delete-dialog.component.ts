import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICmContext } from 'app/shared/model/cm-context.model';
import { CmContextService } from './cm-context.service';

@Component({
    selector: 'jhi-cm-context-delete-dialog',
    templateUrl: './cm-context-delete-dialog.component.html'
})
export class CmContextDeleteDialogComponent {
    cmContext: ICmContext;

    constructor(private cmContextService: CmContextService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.cmContextService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'cmContextListModification',
                content: 'Deleted an cmContext'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-cm-context-delete-popup',
    template: ''
})
export class CmContextDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ cmContext }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CmContextDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.cmContext = cmContext;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
